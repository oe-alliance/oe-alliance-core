#!/usr/bin/env python3
"""Patch the platform-util dvb.ko (gbquad4kpro, vuduo4klite) so multistream (MIS)
transponders lock on the BCM45308X tuner.

dev_fe_73xx_set_frontend picks a FIXED Nexus acquisition mode from the DVB
modulation (QPSK/8PSK/APSK) even when a stream id (ISI) is requested, so a
multistream/ACM transponder never locks.  The working VU+ Duo 4K SE driver forces
NEXUS_FrontendSatelliteMode_eDvbs2Acm (0x12) whenever stream_id != -1.  This patch
does the same: the 14 instructions that decode DTV_STREAM_ID into the Nexus
misMode / streamId / plsMode / plsCode fields are rewritten in place so that they
also store mode = 0x12.  Tunes without a stream id (stream_id == -1) are untouched.
The file size does not change.

The script does not depend on a particular driver build: it parses the ELF, finds
dev_fe_73xx_set_frontend, locates the stream id block by its instruction pattern and
derives the stack offsets (they differ between builds) from the block itself.  If
anything does not look as expected it stops with exit status 2 and leaves the file
untouched.  An already patched file is reported and left alone (exit 0).

Usage: patch_dvb_acm.py [--check] <path/to/dvb.ko>
  --check   only report what would be done, do not write
Exit status: 0 patched / already patched, 2 unrecognised build (file untouched), 1 usage.

Tested against: gbquad4kpro 20200723, 20250326, 20260824 and vuduo4klite 20260911.

Recipe use (same pattern as patch_dvb_init.py; dvb.ko is installed to the same dir):
    SRC_URI += "file://patch_dvb_acm.py"
    DEPENDS += "python3-native"
    do_install:append() {
        python3 ${UNPACKDIR}/patch_dvb_acm.py ${D}/home/root/platform/dvb.ko \\
            || bbwarn "dvb.ko multistream patch not applied (unrecognised dvb.ko build)"
    }
"""
import hashlib
import struct
import sys

FUNC = "dev_fe_73xx_set_frontend"
ACM_MODE = 0x12                                 # NEXUS_FrontendSatelliteMode_eDvbs2Acm

# fixed instruction words of the stream id block (ARM, little endian)
LSR_R1_R3_26 = 0xe1a01d23                       # lsr   r1, r3, #26        (pls_mode)
MOV_R2_1 = 0xe3a02001                           # mov   r2, #1
CMP_R1_R2 = 0xe1510002                          # cmp   r1, r2
UBFX_R0_R3 = 0xe7f10453                         # ubfx  r0, r3, #8, #18    (pls_code)
STRB_R3_SP = 0xe5cd3000                         # strb  r3, [sp, #x]       (streamId)
STRB_R2_SP = 0xe5cd2000                         # strb  r2, [sp, #x]       (misMode)
MOVEQ_R3_2 = 0x03a03002
BEQ_P3 = 0x0a000003
CMP_R1_2 = 0xe3510002
STRNE_R2_SP = 0x158d2000                        # strne r2, [sp, #x]
BNE_P1 = 0x1a000001
MOV_R3_0 = 0xe3a03000
STR_R3_SP = 0xe58d3000                          # str   r3, [sp, #x]
STR_R0_SP = 0xe58d0000                          # str   r0, [sp, #x]
ADD_R0_SP = 0xe28d0000                          # add   r0, sp, #x
TST_R3_FLAG = 0xe3130301                        # tst   r3, #0x4000000     (FE_CAN_MULTISTREAM)
CMN_R3_1 = 0xe3730001                           # cmn   r3, #1             (stream_id == -1 ?)
BLOCK_WORDS = 14


class Refuse(Exception):
    pass


def u32(b, o):
    return struct.unpack_from("<I", b, o)[0]


def parse_elf(d):
    """Return (text_file_offset, func_start, func_size) for FUNC in .text."""
    if d[:4] != b"\x7fELF" or d[4] != 1 or d[5] != 1:
        raise Refuse("not a 32-bit little endian ELF")
    if struct.unpack_from("<H", d, 18)[0] != 40:
        raise Refuse("not an ARM object")
    shoff = u32(d, 32)
    shentsize, shnum, shstrndx = struct.unpack_from("<HHH", d, 46)
    raw = [struct.unpack_from("<IIIIIIIIII", d, shoff + i * shentsize) for i in range(shnum)]
    stro = raw[shstrndx][4]
    secs = {}
    for i, r in enumerate(raw):
        s = stro + r[0]
        secs[d[s:d.index(b"\0", s)].decode()] = (i, r)
    for need in (".text", ".symtab", ".strtab"):
        if need not in secs:
            raise Refuse("no " + need)
    text_idx, text = secs[".text"]
    sym, strtab = secs[".symtab"][1], secs[".strtab"][1]
    found = []
    for o in range(sym[4], sym[4] + sym[5], 16):
        st_name, st_value, st_size, st_info, _other, st_shndx = struct.unpack_from("<IIIBBH", d, o)
        if st_shndx != text_idx or (st_info & 0xf) != 2:
            continue
        s = strtab[4] + st_name
        if d[s:d.index(b"\0", s)].decode() == FUNC:
            found.append((st_value, st_size))
    if len(found) != 1:
        raise Refuse("%s not found (%d symbols)" % (FUNC, len(found)))
    return text[4], found[0][0], found[0][1]


def old_block(sid):
    """The 14 words of the original code for a streamId store at [sp,#sid]."""
    mis, pls, code = sid - 1, sid + 3, sid + 7
    return [LSR_R1_R3_26, MOV_R2_1, CMP_R1_R2, UBFX_R0_R3,
            STRB_R3_SP | sid, STRB_R2_SP | mis, MOVEQ_R3_2, BEQ_P3,
            CMP_R1_2, STRNE_R2_SP | pls, BNE_P1, MOV_R3_0,
            STR_R3_SP | pls, STR_R0_SP | code]


def new_block(sid):
    """Same stores as before, plus mode = ACM.  mode is the first member of the settings
    struct (at [sp,#mode]); misMode is 0x70 bytes after it in every Nexus build seen."""
    mis, pls, code = sid - 1, sid + 3, sid + 7
    mode = mis - 0x70
    return [LSR_R1_R3_26,
            UBFX_R0_R3,
            STRB_R3_SP | sid,                   # streamId = ISI
            MOV_R2_1,
            STRB_R2_SP | mis,                   # misMode  = 1
            0xe3a03000 | ACM_MODE,              # mov r3, #0x12
            STR_R3_SP | mode,                   # mode     = eDvbs2Acm
            0xe3a03001,                         # mov r3, #1   (Root)
            0xe3510001,                         # cmp r1, #1
            0x03a03002,                         # moveq r3, #2 (Gold)
            0xe3510002,                         # cmp r1, #2
            0x03a03000,                         # moveq r3, #0 (Combo -> auto)
            STR_R3_SP | pls,                    # plsMode
            STR_R0_SP | code]                   # plsCode


def find_sites(words, builder, sid_pos):
    """(index, sid) for every i where words[i:i+14] == builder(sid); sid is the immediate of
    the streamId store, which sits at word i+sid_pos (it moved in the rewritten block)."""
    hits = []
    for i in range(len(words) - BLOCK_WORDS):
        if words[i] != LSR_R1_R3_26:
            continue
        sid = words[i + sid_pos] & 0xfff
        if 0x20 <= sid < 0xf00 and words[i:i + BLOCK_WORDS] == builder(sid):
            hits.append((i, sid))
    return hits


def patch(data):
    toff, fstart, fsize = parse_elf(data)
    if fsize % 4 or fsize < 4 * BLOCK_WORDS:
        raise Refuse("unexpected function size %d" % fsize)
    base = toff + fstart
    words = [u32(data, base + 4 * i) for i in range(fsize // 4)]

    new_hits = find_sites(words, new_block, 2)
    old_hits = find_sites(words, old_block, 4)
    if new_hits and not old_hits:
        return None, dict(already=True, at=fstart + 4 * new_hits[0][0])
    if len(old_hits) != 1:
        raise Refuse("stream id block not recognised (%d candidates)" % len(old_hits))
    i, sid = old_hits[0]
    mode = sid - 1 - 0x70

    # sanity checks that this really is the satellite tune routine of this build
    if ADD_R0_SP | mode not in words:
        raise Refuse("settings struct base [sp,#%#x] not passed to GetDefaultSatelliteSettings" % mode)
    if sum(1 for w in words if w == STR_R3_SP | mode) < 2:
        raise Refuse("mode stores not found at [sp,#%#x]" % mode)
    if TST_R3_FLAG not in words[:i] or CMN_R3_1 not in words[:i]:
        raise Refuse("FE_CAN_MULTISTREAM / stream_id == -1 checks not found before the block")

    out = bytearray(data)
    for k, x in enumerate(new_block(sid)):
        struct.pack_into("<I", out, base + 4 * (i + k), x)
    return bytes(out), dict(already=False, at=fstart + 4 * i, sid=sid, mode=mode)


def main():
    args = sys.argv[1:]
    check = False
    if args and args[0] == "--check":
        check, args = True, args[1:]
    if len(args) != 1:
        print(__doc__)
        return 1
    path = args[0]
    with open(path, "rb") as f:
        data = f.read()
    try:
        out, info = patch(data)
    except Refuse as e:
        print("patch_dvb_acm: %s - NOT patching %s" % (e, path))
        return 2
    if info["already"]:
        print("patch_dvb_acm: %s is already patched (block at .text+%#x), nothing to do" % (path, info["at"]))
        return 0
    if check:
        print("patch_dvb_acm: %s can be patched (block at .text+%#x, mode slot [sp,#%#x]); not writing (--check)"
              % (path, info["at"], info["mode"]))
        return 0
    assert len(out) == len(data)
    with open(path, "wb") as f:
        f.write(out)
    print("patch_dvb_acm: patched %s (block at .text+%#x, mode slot [sp,#%#x], sha256 %s)"
          % (path, info["at"], info["mode"], hashlib.sha256(out).hexdigest()))
    return 0


if __name__ == "__main__":
    sys.exit(main())
