#!/usr/bin/env python3
"""Repair DM9x0 bcm7439 ALSA private-data and SFIFO restart races."""

from __future__ import annotations

import argparse
import hashlib
import struct
from pathlib import Path


FUNCTION_PREFIX = bytes.fromhex(
    "f0432de9"  # push {r4-r9, lr}
    "0060a0e1"  # mov r6, r0
    "084090e5"  # ldr r4, [r0, #8]
    "0cd04de2"  # sub sp, sp, #12
    "b07090e5"  # ldr r7, [r0, #0xb0]
    "005094e5"  # ldr r5, [r4]
)

ACK_RACE_PREFIX = bytes.fromhex(
    "0c5097e5"  # ldr r5, [r7, #0xc]
    "283293e5"  # ldr r3, [r3, #0x228]
    "f00195e5"  # ldr r0, [r5, #0x1f0]
    "0231c3e3"  # bic r3, r3, #0x80000000
    "043083e0"  # add r3, r3, r4
    "300090e5"  # ldr r0, [r0, #0x30]
    "026083e1"  # orr r6, r3, r2
)

HW_PARAMS_PREFIX = bytes.fromhex(
    "0030a0e1"  # mov r3, r0
    "000000e3"  # movw r0, #0 (relocated printk format)
    "30402de9"  # push {r4, r5, lr}
    "000040e3"  # movt r0, #0 (relocated printk format)
    "084093e5"  # ldr r4, [r3, #8]
    "0cd04de2"  # sub sp, sp, #12
    "7c1191e5"  # ldr r1, [r1, #0x17c]
    "b05093e5"  # ldr r5, [r3, #0xb0]
    "feffffeb"  # bl printk (relocated)
    "003094e5"  # ldr r3, [r4]
)

# Word offsets relative to bcm_pcm_playback_open().
PATCHES = {
    0x04: (0xE1A06000, 0xE5904000),  # ldr r4, [r0]
    0x08: (0xE5904008, 0xE5944134),  # ldr r4, [r4, #0x134]
    0x68: (0xE1A02006, 0xE5942020),  # ldr r2, [r4, #0x20]
    0x108: (0xE3000000, 0xE5940020),  # ldr r0, [r4, #0x20]
    0x10C: (0xE3400000, 0xE5804008),  # str r4, [r0, #8]
    0x110: (0xEBFFFFFE, 0xE320F000),  # nop (remove diagnostic printk)
}

# These words originally form a relocated printk sequence.  The relocations
# must become R_ARM_NONE or the kernel module loader corrupts the new opcodes.
RELOCATIONS = {
    0x108: 43,  # R_ARM_MOVW_ABS_NC
    0x10C: 44,  # R_ARM_MOVT_ABS
    0x110: 28,  # R_ARM_CALL
}

HW_ERROR_RELOCATIONS = {
    0x94: 43,
    0x98: 44,
    0x9C: 28,
    0xA4: 43,
    0xA8: 44,
    0xAC: 28,
}


def find_unique(data: bytearray, signature: bytes, name: str) -> int:
    matches = []
    cursor = 0
    while True:
        match = data.find(signature, cursor)
        if match < 0:
            break
        matches.append(match)
        cursor = match + 1
    if len(matches) != 1:
        raise SystemExit(
            "expected exactly one %s signature, found %d" % (name, len(matches))
        )
    return matches[0]


def arm_branch(source: int, target: int, condition: int = 0xE) -> int:
    displacement = target - (source + 8)
    if displacement % 4:
        raise SystemExit("unaligned ARM branch")
    words = displacement // 4
    if not -(1 << 23) <= words < (1 << 23):
        raise SystemExit("ARM branch target is out of range")
    return (condition << 28) | 0x0A000000 | (words & 0x00FFFFFF)


def patch_word(
    data: bytearray, offset: int, expected: int, replacement: int
) -> None:
    actual = struct.unpack_from("<I", data, offset)[0]
    if actual != expected:
        raise SystemExit(
            "unexpected ARM word at 0x%x: got 0x%08x, expected 0x%08x"
            % (offset, actual, expected)
        )
    struct.pack_into("<I", data, offset, replacement)


def elf_address_for_file_offset(data: bytearray, file_offset: int) -> int:
    section_offset = struct.unpack_from("<I", data, 0x20)[0]
    section_entry_size = struct.unpack_from("<H", data, 0x2E)[0]
    section_count = struct.unpack_from("<H", data, 0x30)[0]
    for section_index in range(section_count):
        header = section_offset + section_index * section_entry_size
        flags = struct.unpack_from("<I", data, header + 8)[0]
        address = struct.unpack_from("<I", data, header + 12)[0]
        offset = struct.unpack_from("<I", data, header + 16)[0]
        size = struct.unpack_from("<I", data, header + 20)[0]
        if flags & 0x4 and offset <= file_offset < offset + size:
            return address + file_offset - offset
    raise SystemExit("patched function is not inside an executable ELF section")


def disable_relocations(
    data: bytearray, relocation_targets: dict[int, int]
) -> None:
    """Turn selected ELF32 ARM REL entries into R_ARM_NONE entries."""
    if data[:4] != b"\x7fELF" or data[4] != 1 or data[5] != 1:
        raise SystemExit("expected a little-endian ELF32 module")

    section_offset = struct.unpack_from("<I", data, 0x20)[0]
    section_entry_size = struct.unpack_from("<H", data, 0x2E)[0]
    section_count = struct.unpack_from("<H", data, 0x30)[0]
    if section_entry_size < 40:
        raise SystemExit("invalid ELF32 section-header size")

    found: dict[int, int] = {}
    for section_index in range(section_count):
        header = section_offset + section_index * section_entry_size
        section_type = struct.unpack_from("<I", data, header + 4)[0]
        if section_type != 9:  # SHT_REL
            continue
        rel_offset = struct.unpack_from("<I", data, header + 16)[0]
        rel_size = struct.unpack_from("<I", data, header + 20)[0]
        rel_entry_size = struct.unpack_from("<I", data, header + 36)[0] or 8
        if rel_entry_size < 8 or rel_size % rel_entry_size:
            raise SystemExit("invalid ELF32 REL section")

        for entry in range(rel_offset, rel_offset + rel_size, rel_entry_size):
            target, info = struct.unpack_from("<II", data, entry)
            expected_type = relocation_targets.get(target)
            if expected_type is None:
                continue
            actual_type = info & 0xFF
            if actual_type != expected_type:
                raise SystemExit(
                    "unexpected relocation type at 0x%x: got %d, expected %d"
                    % (target, actual_type, expected_type)
                )
            if target in found:
                raise SystemExit("duplicate relocation at 0x%x" % target)
            struct.pack_into("<I", data, entry + 4, info & ~0xFF)
            found[target] = actual_type

    missing = sorted(set(relocation_targets) - set(found))
    if missing:
        raise SystemExit(
            "missing relocations: %s"
            % ", ".join("0x%x" % target for target in missing)
        )


def move_relocation(
    data: bytearray, old_target: int, new_target: int, expected_type: int
) -> None:
    section_offset = struct.unpack_from("<I", data, 0x20)[0]
    section_entry_size = struct.unpack_from("<H", data, 0x2E)[0]
    section_count = struct.unpack_from("<H", data, 0x30)[0]
    match = None
    for section_index in range(section_count):
        header = section_offset + section_index * section_entry_size
        if struct.unpack_from("<I", data, header + 4)[0] != 9:  # SHT_REL
            continue
        rel_offset = struct.unpack_from("<I", data, header + 16)[0]
        rel_size = struct.unpack_from("<I", data, header + 20)[0]
        rel_entry_size = struct.unpack_from("<I", data, header + 36)[0] or 8
        for entry in range(rel_offset, rel_offset + rel_size, rel_entry_size):
            target, info = struct.unpack_from("<II", data, entry)
            if target == new_target:
                raise SystemExit("relocation target 0x%x is already occupied" % new_target)
            if target != old_target:
                continue
            if match is not None:
                raise SystemExit("duplicate relocation at 0x%x" % old_target)
            if info & 0xFF != expected_type:
                raise SystemExit(
                    "unexpected relocation type at 0x%x: got %d, expected %d"
                    % (old_target, info & 0xFF, expected_type)
                )
            match = entry
    if match is None:
        raise SystemExit("missing relocation at 0x%x" % old_target)
    struct.pack_into("<I", data, match, new_target)


def sha256(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def patch_driver(source: Path, destination: Path) -> None:
    source_data = source.read_bytes()
    data = bytearray(source_data)
    function_offset = find_unique(
        data, FUNCTION_PREFIX, "bcm_pcm_playback_open"
    )
    ack_offset = find_unique(data, ACK_RACE_PREFIX, "bcm_pcm_playback_ack race")
    hw_offset = find_unique(data, HW_PARAMS_PREFIX, "bcm_pcm_hw_params")

    for relative_offset, (expected, replacement) in PATCHES.items():
        offset = function_offset + relative_offset
        patch_word(data, offset, expected, replacement)

    ack_address = elf_address_for_file_offset(data, ack_offset)
    hw_address = elf_address_for_file_offset(data, hw_offset)
    cave_a_offset = hw_offset + 0x94
    cave_b_offset = hw_offset + 0xA4
    cave_a_address = hw_address + 0x94
    cave_b_address = hw_address + 0xA4
    ack_return_address = ack_address + 0x60
    ack_resume_address = ack_address + 0x14

    # Skip the two old hw_params error-print blocks and reuse them as a split
    # trampoline for an immediate SFIFO NULL check in playback_ack().
    patch_word(data, hw_offset + 0x54, 0x1A00000E, 0x1AFFFFFF)
    patch_word(data, hw_offset + 0x7C, 0x1A000008, 0x1AFFFFFF)
    patch_word(
        data,
        ack_offset + 0x0C,
        0xE3C33102,
        arm_branch(ack_address + 0x0C, cave_a_address),
    )
    patch_word(data, cave_a_offset + 0x00, 0xE3000000, 0xE3500000)
    patch_word(
        data,
        cave_a_offset + 0x04,
        0xE3400000,
        arm_branch(cave_a_address + 0x04, ack_return_address, 0x0),
    )
    patch_word(
        data,
        cave_a_offset + 0x08,
        0xEBFFFFFE,
        arm_branch(cave_a_address + 0x08, cave_b_address),
    )
    patch_word(data, cave_b_offset + 0x00, 0xE3000000, 0xE3C33102)
    patch_word(data, cave_b_offset + 0x04, 0xE3400000, 0xE0833004)
    patch_word(data, cave_b_offset + 0x08, 0xEBFFFFFE, 0xE5874018)
    patch_word(
        data,
        cave_b_offset + 0x0C,
        0xEAFFFFF2,
        arm_branch(cave_b_address + 0x0C, ack_resume_address),
    )

    # The Rearm path reloads the same optional SFIFO pointer.  Make its load
    # and call conditional, and move the external-call relocation one word.
    patch_word(data, ack_offset + 0x54, 0xE5930030, 0xE3530000)
    patch_word(data, ack_offset + 0x58, 0xEBFFFFFE, 0x15930030)
    patch_word(data, ack_offset + 0x5C, 0xE5874018, 0x1BFFFFFE)
    move_relocation(data, ack_address + 0x58, ack_address + 0x5C, 28)

    function_address = elf_address_for_file_offset(data, function_offset)
    disable_relocations(
        data,
        {
            function_address + relative_offset: expected_type
            for relative_offset, expected_type in RELOCATIONS.items()
        }
        | {
            hw_address + relative_offset: expected_type
            for relative_offset, expected_type in HW_ERROR_RELOCATIONS.items()
        },
    )

    destination.write_bytes(data)
    print("input_sha256=%s" % sha256(source_data))
    print("output_sha256=%s" % sha256(data))
    print("function_file_offset=0x%x" % function_offset)
    print("ack_race_file_offset=0x%x" % ack_offset)
    print("hw_params_file_offset=0x%x" % hw_offset)


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("source", type=Path)
    parser.add_argument("destination", type=Path)
    args = parser.parse_args()
    patch_driver(args.source, args.destination)


if __name__ == "__main__":
    main()
