#!/usr/bin/env python3
"""
patch_nexus_hlg.py - make Broadcom Nexus (nexus.ko) recognise HLG signalled in the VUI.

Problem: NEXUS_P_TransferCharacteristicsToEotf_isrsafe(transfer, preferred) only
returns HLG when the *preferred* transfer characteristics (from the HEVC
alternative-transfer-characteristics SEI) is HLG. A stream that signals HLG only
in the VUI (transfer_characteristics = 18) is reported as SDR (eotf 0), so the
HDMI HDR (DRM) infoframe is never sent and the TV stays in SDR.

Fix: the function returns HLG if EITHER value is HLG. Both known function
layouts keep their size, so nothing else moves.

    before                      after
    cmp   r1, #0xf              cmp   r1, #0xf
    bne   L                     cmpne r0, #0xf
    mov   r0, #1                moveq r0, #1
    bx    lr                    bxeq  lr
  L:cmp   r0, #0xe              cmp   r0, #0xe      ; PQ / HDR10 (unchanged)
    moveq r0, #2                moveq r0, #2
    movne r0, #0                movne r0, #0
    bx    lr                    bx    lr

The GB Quad 4K Pro 20261020 driver has a 104-byte variant with transfer-value
validation and error logging. Combine its AND/CMP into ANDS to make room for
the additional HLG comparison, keeping its logging call and relocations in place.

Usage:
    python3 patch_nexus_hlg.py nexus.ko              # writes nexus.ko.patched
    python3 patch_nexus_hlg.py nexus.ko --check      # only report, change nothing
    python3 patch_nexus_hlg.py nexus.ko -o out.ko

The patcher refuses to change anything unless the function's current bytes match
the known original exactly, so it can't corrupt a different build by accident.
Pure Python 3 standard library.
"""
from argparse import ArgumentParser, RawDescriptionHelpFormatter
from hashlib import sha256
from struct import pack_into, unpack_from
from sys import exit

FUNC = b'NEXUS_P_TransferCharacteristicsToEotf_isrsafe'
ORIGINAL = [0xE351000F, 0x1A000001, 0xE3A00001, 0xE12FFF1E,
	0xE350000E, 0x03A00002, 0x13A00000, 0xE12FFF1E]
PATCHED = [0xE351000F, 0x1350000F, 0x03A00001, 0x012FFF1E,
	0xE350000E, 0x03A00002, 0x13A00000, 0xE12FFF1E]
ORIGINAL_VALIDATED = [
	0xE351000F, 0x0A00000F, 0xE2400001, 0xE350000D, 0xE92D4008,
	0x8A000007, 0xE3A03001, 0xE1A00013, 0xE3013CFB, 0xE0033000,
	0xE3530000, 0x1A000007, 0xE3100A02, 0x1A000007, 0xE59F1020,
	0xE59F0020, 0xEBFFFFFE, 0xEA000001, 0xE3A00001, 0xE12FFF1E,
	0xE3A00000, 0xE8BD8008, 0xE3A00002, 0xE8BD8008, 0x00008865,
	0x0000047C
]
PATCHED_VALIDATED = [
	0xE351000F, 0x1350000F, 0x0A00000E, 0xE2400001, 0xE350000D,
	0xE92D4008, 0x8A000006, 0xE3A03001, 0xE1A00013, 0xE3013CFB,
	0xE0133000, *ORIGINAL_VALIDATED[11:]
]


def find_function(d):
	if d[:4] != b'\x7fELF' or d[4] != 1 or d[5] != 1:
		exit('not a 32-bit little-endian ELF file')
	sectionOffset, = unpack_from('<I', d, 0x20)
	sectionEntrySize, sectionCount = unpack_from('<HH', d, 0x2E)
	sections = [unpack_from('<IIIIIIIIII', d, sectionOffset + x * sectionEntrySize) for x in range(sectionCount)]
	# (name,type,flags,addr,offset,size,link,info,align,entsize)
	symbolTable = next((x for x in sections if x[1] == 2), None)
	if symbolTable is None:
		exit('no symbol table in this module (stripped) - cannot locate the function safely')
	stringTable = sections[symbolTable[6]]
	for index in range(symbolTable[5] // 16):
		nameOffset, value, size, info, other, sectionIndex = unpack_from('<IIIBBH', d, symbolTable[4] + index * 16)
		nameStart = stringTable[4] + nameOffset
		if d[nameStart:nameStart + len(FUNC) + 1] == FUNC + b'\x00':
			section = sections[sectionIndex]
			return section[4] + value, size, sectionIndex
	exit(f'function {FUNC.decode()} not found in this module')


def words(d, off, n):
	return list(unpack_from(f'<{n}I', d, off))


def main():
	parser = ArgumentParser(description=__doc__, formatter_class=RawDescriptionHelpFormatter)
	parser.add_argument('module')
	parser.add_argument('-o', '--output')
	parser.add_argument('--check', action='store_true', help='report only, write nothing')
	args = parser.parse_args()

	with open(args.module, 'rb') as source:
		data = bytearray(source.read())
	offset, size, sectionIndex = find_function(data)
	print(f'function at file offset {offset:#x}, size {size} bytes')
	print('sha256 before:', sha256(data).hexdigest())
	for original, patched in ((ORIGINAL, PATCHED), (ORIGINAL_VALIDATED, PATCHED_VALIDATED)):
		if size != len(original) * 4:
			continue
		current = words(data, offset, len(original))
		if current == patched:
			print('Already patched. Nothing to do.')
			return 0
		if current == original:
			break
	else:
		print('Function bytes do NOT match a known original, refusing to patch.')
		print('found :', ' '.join(f'{x:08x}' for x in words(data, offset, min(size // 4, len(ORIGINAL_VALIDATED)))))
		return 1
	print('Original code matches the known build: patch can be applied.')
	if args.check:
		return 0
	pack_into(f'<{len(patched)}I', data, offset, *patched)
	output = args.output or f'{args.module}.patched'
	with open(output, 'wb') as destination:
		destination.write(data)
	print('sha256 after :', sha256(data).hexdigest())
	print('wrote', output)
	return 0


if __name__ == '__main__':
	exit(main())
