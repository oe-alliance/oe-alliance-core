#!/usr/bin/env python3

"""Allow Kodi to release the decoder owned by Dinobot showiframe.

The vendor binaries are not rebuilt from source, but retain their ELF symbol
tables.  Patch the existing signal handler so SIGUSR1 frees only the video
decoder while showiframe and the HDMI/GLES setup remain active.
"""

from pathlib import Path
import struct
import sys


def get_cstring(data, offset):
    if offset >= len(data):
        return b""
    end = data.find(b"\x00", offset)
    return b"" if end < 0 else data[offset:end]


def elf_info(data):
    if data[:4] != b"\x7fELF" or data[4] != 1 or data[5] != 1:
        raise RuntimeError("showiframe is not a little-endian ELF32 binary")

    e_shoff = struct.unpack_from("<I", data, 0x20)[0]
    e_shentsize = struct.unpack_from("<H", data, 0x2E)[0]
    e_shnum = struct.unpack_from("<H", data, 0x30)[0]
    sections = []

    for index in range(e_shnum):
        offset = e_shoff + index * e_shentsize
        if offset + 40 > len(data):
            raise RuntimeError("truncated ELF section table")
        sh = struct.unpack_from("<IIIIIIIIII", data, offset)
        sections.append({
            "type": sh[1],
            "addr": sh[3],
            "offset": sh[4],
            "size": sh[5],
            "link": sh[6],
            "entsize": sh[9],
        })

    symbols = {}
    for section in sections:
        if section["type"] not in (2, 11) or section["entsize"] != 16:
            continue
        if section["link"] >= len(sections):
            continue
        strtab = sections[section["link"]]
        strings = data[strtab["offset"]:strtab["offset"] + strtab["size"]]
        start = section["offset"]
        end = start + section["size"]
        for sym_offset in range(start, end, 16):
            values = struct.unpack_from("<IIIBBH", data, sym_offset)
            st_name, st_value, st_size, st_info, _st_other, st_shndx = values
            name = get_cstring(strings, st_name)
            if not name:
                continue
            decoded = name.decode("ascii", errors="ignore")
            candidate = {
                "value": st_value,
                "size": st_size,
                "info": st_info,
                "section": st_shndx,
            }
            # Prefer a defined symbol from .symtab over an undefined dynamic
            # declaration bearing the same name.
            if decoded not in symbols or (symbols[decoded]["section"] == 0 and st_shndx != 0):
                symbols[decoded] = candidate
    return sections, symbols


def symbol_address(symbol):
    address = symbol["value"]
    if symbol["info"] & 0x0F == 2:  # STT_FUNC: bit zero marks Thumb code.
        address &= ~1
    return address


def virtual_file_offset(sections, address):
    for section in sections:
        if section["type"] == 8:
            continue
        if section["addr"] <= address < section["addr"] + section["size"]:
            return section["offset"] + address - section["addr"]
    raise RuntimeError("ELF address lies outside file-backed sections")


def symbol_file_offset(sections, symbol):
    if symbol["section"] >= len(sections):
        raise RuntimeError("ELF symbol has an invalid section")
    return virtual_file_offset(sections, symbol_address(symbol))


def decode_arm_branch(address, instruction):
    if instruction & 0x0E000000 != 0x0A000000:
        raise RuntimeError("expected an ARM branch instruction")
    displacement = (instruction & 0x00FFFFFF) << 2
    if displacement & 0x02000000:
        displacement -= 0x04000000
    return address + 8 + displacement


def encode_arm_branch(address, target, link=False, condition=0xE):
    displacement = target - (address + 8)
    if displacement & 3 or not -0x02000000 <= displacement < 0x02000000:
        raise RuntimeError("invalid ARM branch target")
    return ((condition & 0xF) << 28) | 0x0A000000 | ((1 if link else 0) << 24) | ((displacement >> 2) & 0x00FFFFFF)


def thumb_branch_bits(displacement):
    encoded = displacement & 0x01FFFFFF
    s = (encoded >> 24) & 1
    i1 = (encoded >> 23) & 1
    i2 = (encoded >> 22) & 1
    j1 = ((~i1) ^ s) & 1
    j2 = ((~i2) ^ s) & 1
    return encoded, s, j1, j2


def encode_thumb_bl(address, target):
    displacement = target - (address + 4)
    if displacement & 1 or not -0x01000000 <= displacement < 0x01000000:
        raise RuntimeError("invalid Thumb BL target")
    encoded, s, j1, j2 = thumb_branch_bits(displacement)
    first = 0xF000 | (s << 10) | ((encoded >> 12) & 0x03FF)
    second = 0xD000 | (j1 << 13) | (j2 << 11) | ((encoded >> 1) & 0x07FF)
    return first, second


def decode_thumb_blx(address, first, second):
    if first & 0xF800 != 0xF000 or second & 0xD001 != 0xC000:
        raise RuntimeError("expected a Thumb BLX instruction")
    s = (first >> 10) & 1
    j1 = (second >> 13) & 1
    j2 = (second >> 11) & 1
    i1 = (~(j1 ^ s)) & 1
    i2 = (~(j2 ^ s)) & 1
    encoded = (s << 24) | (i1 << 23) | (i2 << 22)
    encoded |= (first & 0x03FF) << 12
    encoded |= ((second >> 1) & 0x03FF) << 2
    if encoded & 0x01000000:
        encoded -= 0x02000000
    return ((address + 4) & ~3) + encoded


def encode_thumb_blx(address, target):
    pc = (address + 4) & ~3
    displacement = target - pc
    if displacement & 3 or not -0x01000000 <= displacement < 0x01000000:
        raise RuntimeError("invalid Thumb BLX target")
    encoded, s, j1, j2 = thumb_branch_bits(displacement)
    first = 0xF000 | (s << 10) | ((encoded >> 12) & 0x03FF)
    second = 0xC000 | (j1 << 13) | (j2 << 11) | (((encoded >> 2) & 0x03FF) << 1)
    return first, second


def encode_thumb_cond_branch(address, target, condition):
    displacement = target - (address + 4)
    if displacement & 1 or not -256 <= displacement < 256:
        raise RuntimeError("invalid conditional Thumb branch target")
    return 0xD000 | ((condition & 0xF) << 8) | ((displacement >> 1) & 0xFF)


def patch_auth(data, sections, symbols):
    auth = symbols.get("g_authOk")
    if auth is None:
        raise RuntimeError("g_authOk symbol not found")
    offset = symbol_file_offset(sections, auth)
    current = data[offset:offset + 4]
    if current == b"\xff\xff\xff\xff":
        data[offset:offset + 4] = b"\x01\x00\x00\x00"
    elif current != b"\x01\x00\x00\x00":
        raise RuntimeError("unexpected g_authOk initializer")


def patch_arm_handler(data, sections, symbols):
    quit_symbol = symbols["quit"]
    fini = symbol_address(symbols["finiCliMes"])
    video_stop = symbol_address(symbols["hisi_platform_stop_video"])
    video_free = symbol_address(symbols["avplay_handle_video_free"])
    if quit_symbol["size"] < 72:
        raise RuntimeError("ARM showiframe quit handler is too small")

    base = symbol_address(quit_symbol)
    offset = symbol_file_offset(sections, quit_symbol)
    old = list(struct.unpack_from("<18I", data, offset))
    if old[0] == 0xE350000A:
        return "already patched ARM"
    if old[0] != 0xE92D4800 or old[4] & 0xFF000000 != 0xEB000000:
        raise RuntimeError("unknown ARM showiframe quit handler")
    if decode_arm_branch(base + 16, old[4]) != fini:
        raise RuntimeError("unexpected finiCliMes call in ARM quit handler")

    unlink_target = decode_arm_branch(base + 52, old[13])
    exit_target = decode_arm_branch(base + 60, old[15])
    pid_file = old[17] + base + 52
    if get_cstring(data, virtual_file_offset(sections, pid_file)) != b"/dev/si.pid":
        raise RuntimeError("unexpected ARM PID-file literal")

    replacement = [
        0xE350000A,
        encode_arm_branch(base + 4, base + 32, condition=0),
        0xE92D4010,
        encode_arm_branch(base + 12, fini, link=True),
        0xE59F0020,
        encode_arm_branch(base + 20, unlink_target, link=True),
        0xE3A00001,
        encode_arm_branch(base + 28, exit_target, link=True),
        0xE92D4010,
        0xE3A00000,
        encode_arm_branch(base + 40, video_stop, link=True),
        0xE3A00000,
        encode_arm_branch(base + 48, video_free, link=True),
        0xE8BD8010,
        pid_file,
        0xE1A00000,
        0xE1A00000,
        0xE1A00000,
    ]
    data[offset:offset + 72] = struct.pack("<18I", *replacement)
    return "patched ARM"


def patch_thumb_u5pvr_handler(data, sections, symbols):
    quit_symbol = symbols["quit"]
    fini = symbol_address(symbols["finiCliMes"])
    video_stop = symbol_address(symbols["hisi_platform_stop_video"])
    destroy = symbol_address(symbols["hisi_platform_destory_esavplay"])
    if quit_symbol["size"] < 48:
        raise RuntimeError("U5PVR showiframe quit handler is too small")

    base = symbol_address(quit_symbol)
    offset = symbol_file_offset(sections, quit_symbol)
    old = list(struct.unpack_from("<24H", data, offset))
    if old[0] == 0x280A:
        return "already patched Thumb/U5PVR"
    if old[:4] != [0xB580, 0xB082, 0xAF00, 0x6078]:
        raise RuntimeError("unknown Thumb/U5PVR showiframe quit handler")

    unlink_target = decode_thumb_blx(base + 30, old[15], old[16])
    exit_target = decode_thumb_blx(base + 36, old[18], old[19])
    pid_file = struct.unpack_from("<I", data, offset + 44)[0] + base + 30
    if get_cstring(data, virtual_file_offset(sections, pid_file)) != b"/dev/si.pid":
        raise RuntimeError("unexpected Thumb/U5PVR PID-file literal")

    replacement = bytearray(b"\x00" * 48)

    def put16(relative, value):
        struct.pack_into("<H", replacement, relative, value)

    def put_branch(relative, values):
        struct.pack_into("<HH", replacement, relative, *values)

    put16(0, 0x280A)  # cmp r0, #SIGUSR1
    put16(2, encode_thumb_cond_branch(base + 2, base + 24, 0))
    put16(4, 0xB510)  # push {r4, lr}
    put_branch(6, encode_thumb_bl(base + 6, fini))
    put16(10, 0x4807)  # ldr r0, [pc, #28] -> absolute PID-file address
    put_branch(12, encode_thumb_blx(base + 12, unlink_target))
    put16(16, 0x2001)  # movs r0, #1
    put_branch(18, encode_thumb_blx(base + 18, exit_target))
    put16(22, 0xBF00)
    put16(24, 0xB510)  # release_video: push {r4, lr}
    put_branch(26, encode_thumb_bl(base + 26, video_stop))
    put16(30, 0x2000)  # ES AVPlay occupies slot zero after init
    put_branch(32, encode_thumb_bl(base + 32, destroy))
    put16(36, 0xBD10)  # pop {r4, pc}
    put16(38, 0xBF00)
    struct.pack_into("<I", replacement, 40, pid_file)
    put16(44, 0xBF00)
    put16(46, 0xBF00)
    data[offset:offset + 48] = replacement
    return "patched Thumb/U5PVR"


def patch_showiframe(path):
    data = bytearray(path.read_bytes())
    sections, symbols = elf_info(data)
    patch_auth(data, sections, symbols)

    required = {"quit", "finiCliMes", "hisi_platform_stop_video"}
    if not required.issubset(symbols):
        raise RuntimeError("required showiframe symbols not found")

    if "avplay_handle_video_free" in symbols:
        result = patch_arm_handler(data, sections, symbols)
    elif "hisi_platform_destory_esavplay" in symbols and symbols["quit"]["value"] & 1:
        result = patch_thumb_u5pvr_handler(data, sections, symbols)
    else:
        raise RuntimeError("unsupported Dinobot showiframe binary")

    path.write_bytes(data)
    return result


if len(sys.argv) != 2:
    raise SystemExit("usage: patch-dinobot-showiframe.py /path/to/showiframe")

target = Path(sys.argv[1])
if not target.is_file():
    raise SystemExit("installed showiframe binary is missing")
print(f"{target}: {patch_showiframe(target)}")
