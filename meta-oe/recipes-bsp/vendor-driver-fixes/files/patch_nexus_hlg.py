#!/usr/bin/env python3
"""
patch_nexus_hlg.py - make Broadcom Nexus (nexus.ko) recognise HLG signalled in the VUI.

Problem: NEXUS_P_TransferCharacteristicsToEotf_isrsafe(transfer, preferred) only
returns HLG when the *preferred* transfer characteristics (from the HEVC
alternative-transfer-characteristics SEI) is HLG. A stream that signals HLG only
in the VUI (transfer_characteristics = 18) is reported as SDR (eotf 0), so the
HDMI HDR (DRM) infoframe is never sent and the TV stays in SDR.

Fix: the function returns HLG if EITHER value is HLG. The new code is the same
size as the old code (8 ARM instructions), so nothing else moves.

    before                      after
    cmp   r1, #0xf              cmp   r1, #0xf
    bne   L                     cmpne r0, #0xf
    mov   r0, #1                moveq r0, #1
    bx    lr                    bxeq  lr
  L:cmp   r0, #0xe              cmp   r0, #0xe      ; PQ / HDR10 (unchanged)
    moveq r0, #2                moveq r0, #2
    movne r0, #0                movne r0, #0
    bx    lr                    bx    lr

Usage:
    python3 patch_nexus_hlg.py nexus.ko              # writes nexus.ko.patched
    python3 patch_nexus_hlg.py nexus.ko --check      # only report, change nothing
    python3 patch_nexus_hlg.py nexus.ko -o out.ko

The patcher refuses to change anything unless the function's current bytes match
the known original exactly, so it can't corrupt a different build by accident.
Pure Python 3 standard library.
"""
import argparse
import hashlib
import struct
import sys

FUNC = b'NEXUS_P_TransferCharacteristicsToEotf_isrsafe'
ORIGINAL = [0xE351000F, 0x1A000001, 0xE3A00001, 0xE12FFF1E,
            0xE350000E, 0x03A00002, 0x13A00000, 0xE12FFF1E]
PATCHED = [0xE351000F, 0x1350000F, 0x03A00001, 0x012FFF1E,
           0xE350000E, 0x03A00002, 0x13A00000, 0xE12FFF1E]


def find_function(d):
    if d[:4] != b'\x7fELF' or d[4] != 1 or d[5] != 1:
        sys.exit('not a 32-bit little-endian ELF file')
    shoff, = struct.unpack_from('<I', d, 0x20)
    shentsize, shnum, shstrndx = struct.unpack_from('<HHH', d, 0x2E)
    sh = []
    for i in range(shnum):
        sh.append(struct.unpack_from('<IIIIIIIIII', d, shoff + i * shentsize))
    # (name,type,flags,addr,offset,size,link,info,align,entsize)
    symtab = next((s for s in sh if s[1] == 2), None)
    if symtab is None:
        sys.exit('no symbol table in this module (stripped) - cannot locate the function safely')
    strtab = sh[symtab[6]]
    n = symtab[5] // 16
    for i in range(n):
        name_off, value, size, info, other, shndx = struct.unpack_from('<IIIBBH', d, symtab[4] + i * 16)
        nm_start = strtab[4] + name_off
        if d[nm_start:nm_start + len(FUNC) + 1] == FUNC + b'\x00':
            sec = sh[shndx]
            return sec[4] + value, size, shndx
    sys.exit('function %s not found in this module' % FUNC.decode())


def words(d, off, n):
    return list(struct.unpack_from('<%dI' % n, d, off))


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument('module')
    ap.add_argument('-o', '--output')
    ap.add_argument('--check', action='store_true', help='report only, write nothing')
    a = ap.parse_args()

    d = bytearray(open(a.module, 'rb').read())
    off, size, _ = find_function(d)
    cur = words(d, off, 8)
    print('function at file offset %#x, size %d bytes' % (off, size))
    print('sha256 before:', hashlib.sha256(d).hexdigest())
    if cur == PATCHED:
        print('Already patched. Nothing to do.')
        return 0
    if size != 32 or cur != ORIGINAL:
        print('Function bytes do NOT match the known original, refusing to patch.')
        print('found :', ' '.join('%08x' % w for w in cur))
        print('wanted:', ' '.join('%08x' % w for w in ORIGINAL))
        return 1
    print('Original code matches the known build: patch can be applied.')
    if a.check:
        return 0
    struct.pack_into('<8I', d, off, *PATCHED)
    out = a.output or a.module + '.patched'
    open(out, 'wb').write(d)
    print('sha256 after :', hashlib.sha256(d).hexdigest())
    print('wrote', out)
    return 0


if __name__ == '__main__':
    sys.exit(main())
