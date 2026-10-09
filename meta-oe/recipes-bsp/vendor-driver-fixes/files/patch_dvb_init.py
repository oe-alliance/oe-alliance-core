#!/usr/bin/env python3
"""Patch the platform-util dvb_init (gbquad4kpro, vuduo4klite) to fix the
video judder at non-60Hz refresh rates ("early kick" patch).

dvb_init only re-programs the HDMI colour depth together with a mode change,
which leaves the video timing unlocked on these boxes.  The patch makes the
2160p display routine apply one extra colour-depth change (depth 8 for 500 ms,
then the real depth again) the first time a 2160p mode is set while a video is
decoding, once per boot.  No E2 side workaround is needed afterwards.

The original binary is patched IN PLACE.  The script does not depend on a
particular driver build: it parses the ELF, finds the colour depth block in the
display routine, takes every address (PLT entries, globals, bss) from the
binary itself and writes a new block over the old one.  If anything does not
look as expected it stops with exit status 2 and leaves the file untouched.

Usage: patch_dvb_init.py <path/to/dvb_init>
"""
import struct
import sys

O_DEPTH, O_CSP, O_MODE = -0x174, -0x178, -0x21c      # frame offsets in the routine
O_NEW, O_CUR = 0x220, 0x128                           # NxClient_DisplaySettings copies
O_ST = 0xde4                                          # video decoder status struct (fp - O_ST)
NOP = 0xe1a00000
EQ, NE, LS, AL = 0, 1, 9, 0xe
FP = 11


class Refuse(Exception):
    pass


def u32(b, o):
    return struct.unpack_from("<I", b, o)[0]


class Elf:
    def __init__(self, d):
        if d[:4] != b"\x7fELF" or d[4] != 1 or d[5] != 1:
            raise Refuse("not a 32-bit little endian ELF")
        self.d = d
        shoff = u32(d, 32)
        self.phoff = u32(d, 28)
        phentsize, phnum, shentsize, shnum, shstrndx = struct.unpack_from("<HHHHH", d, 42)
        self.phentsize = phentsize
        raw = [struct.unpack_from("<IIIIIIIIII", d, shoff + i * shentsize) for i in range(shnum)]
        stro = raw[shstrndx][4]
        self.sec = {}
        for r in raw:
            s = stro + r[0]
            name = d[s:d.index(b"\0", s)].decode()
            self.sec[name] = dict(addr=r[3], off=r[4], size=r[5])
        self.loads = []
        for i in range(phnum):
            p = struct.unpack_from("<IIIIIIII", d, self.phoff + i * phentsize)
            if p[0] == 1:
                self.loads.append(dict(idx=i, off=p[1], vaddr=p[2], filesz=p[4], memsz=p[5]))

    def plt_names(self):
        d = self.d
        dynsym, dynstr = self.sec[".dynsym"], self.sec[".dynstr"]
        rel, plt = self.sec[".rel.plt"], self.sec[".plt"]
        got = {}
        for i in range(rel["size"] // 8):
            r_off, r_info = struct.unpack_from("<II", d, rel["off"] + i * 8)
            s = dynstr["off"] + u32(d, dynsym["off"] + (r_info >> 8) * 16)
            got[r_off] = d[s:d.index(b"\0", s)].decode()

        def rot(v):
            r = ((v >> 8) & 0xf) * 2
            x = v & 0xff
            return ((x >> r) | (x << (32 - r))) & 0xffffffff if r else x

        names = {}
        a = plt["addr"] + 20
        while a + 12 <= plt["addr"] + plt["size"]:
            i0, i1, i2 = struct.unpack_from("<3I", d, plt["off"] + a - plt["addr"])
            g = a + 8 + rot(i0) + rot(i1) + (i2 & 0xfff)
            names[got.get(g, "?")] = a
            a += 12
        return names


# ---- tiny ARM assembler -------------------------------------------------
def imm(v):
    for r in range(16):
        x = ((v << (2 * r)) | (v >> (32 - 2 * r))) & 0xffffffff if r else v
        if x < 256:
            return (r << 8) | x
    raise ValueError(hex(v))


def dp(op, rd, rn, v, cond=AL, s=0):
    return (cond << 28) | (1 << 25) | (op << 21) | (s << 20) | (rn << 16) | (rd << 12) | imm(v)


def mov(rd, v, cond=AL): return dp(0xd, rd, 0, v, cond)
def sub(rd, rn, v, cond=AL): return dp(2, rd, rn, v, cond)
def cmpi(rn, v, cond=AL): return dp(0xa, 0, rn, v, cond, 1)
def movw(rd, v, cond=AL): return (cond << 28) | 0x03000000 | ((v >> 12) << 16) | (rd << 12) | (v & 0xfff)
def movt(rd, v, cond=AL): return (cond << 28) | 0x03400000 | ((v >> 12) << 16) | (rd << 12) | (v & 0xfff)


def ldst(load, byte, rd, rn, off):
    up = 1 if off >= 0 else 0
    off = abs(off)
    assert off < 0x1000
    return (AL << 28) | 0x05000000 | (up << 23) | (byte << 22) | (load << 20) | (rn << 16) | (rd << 12) | off


def ldr(rd, rn, off): return ldst(1, 0, rd, rn, off)
def str_(rd, rn, off): return ldst(0, 0, rd, rn, off)
def ldrb(rd, rn, off): return ldst(1, 1, rd, rn, off)
def strb(rd, rn, off): return ldst(0, 1, rd, rn, off)
def ldrb_r(rd, rn, rm): return 0xe7d00000 | (rn << 16) | (rd << 12) | rm


PUSH_R4, POP_R4 = 0xe92d0010, 0xe8bd0010


class Asm:
    def __init__(self, base):
        self.base, self.w, self.lab, self.fix = base, [], {}, []

    def e(self, x):
        self.w.append(x)

    def label(self, n):
        self.lab[n] = self.base + 4 * len(self.w)

    def br(self, kind, tgt, cond=AL):
        self.fix.append((len(self.w), kind, tgt, cond))
        self.w.append(0)

    def done(self):
        for i, kind, t, c in self.fix:
            at = self.base + 4 * i
            tg = self.lab[t] if isinstance(t, str) else t
            self.w[i] = (c << 28) | (0x0b000000 if kind == "bl" else 0x0a000000) | (((tg - (at + 8)) >> 2) & 0xffffff)
        return self.w


def branch_target(w, at):
    """Target of a b/bl instruction (any condition), else None."""
    if (w & 0x0e000000) != 0x0a000000:
        return None
    off = w & 0xffffff
    if off & 0x800000:
        off -= 0x1000000
    return at + 8 + off * 4


def is_bl(w):
    return (w & 0x0f000000) == 0x0b000000


def imm16(lo_word, hi_word):
    lo = ((lo_word >> 4) & 0xf000) | (lo_word & 0xfff)
    hi = ((hi_word >> 4) & 0xf000) | (hi_word & 0xfff)
    return (hi << 16) | lo


# ---- patch --------------------------------------------------------------
def patch(data):
    elf = Elf(data)
    text = elf.sec[".text"]
    tbase, toff = text["addr"], text["off"]
    words = [u32(data, toff + i) for i in range(0, text["size"], 4)]

    def waddr(i):
        return tbase + 4 * i

    def widx(a):
        return (a - tbase) // 4

    plt = elf.plt_names()
    need_imports = ("NxClient_SetDisplaySettings", "NxClient_GetDisplaySettings", "BKNI_Sleep",
                    "memcmp", "NEXUS_SimpleVideoDecoder_GetStatus")
    for n in need_imports:
        if n not in plt:
            raise Refuse("missing import " + n)
    SET, GETD, SLP, MEMCMP, GETST = (plt[n] for n in need_imports)

    s = data.find(b"/proc/stb/video/hdmi_colordepth\0")
    if s < 0:
        raise Refuse("colordepth string not found")
    seg = [l for l in elf.loads if l["off"] <= s < l["off"] + l["filesz"]][0]
    saddr = seg["vaddr"] + s - seg["off"]

    # phase 2 of the display routine: two bl NxClient_GetDisplaySettings, then the colour
    # depth is read ("movw r0,str; movt r0,str; bl read; mov r3,r0; str r3,[fp,#-0x174]")
    p2 = []
    for i in range(4, len(words) - 5):
        if words[i] == movw(0, saddr & 0xffff) and words[i + 1] == movt(0, saddr >> 16) \
                and is_bl(words[i + 2]) and words[i + 3] == 0xe1a03000 and words[i + 4] == str_(3, FP, O_DEPTH) \
                and is_bl(words[i - 1]) and branch_target(words[i - 1], waddr(i - 1)) == GETD \
                and is_bl(words[i - 4]) and branch_target(words[i - 4], waddr(i - 4)) == GETD:
            p2.append(i)
    if len(p2) != 1:
        raise Refuse("display routine not recognised (%d candidates)" % len(p2))
    A = waddr(p2[0] + 5)

    # end of the block: "b EPI", nop, EPI = mov r3,#0; mov r0,r3; sub sp,fp,#4; pop {fp,pc}
    epi_seq = [NOP, mov(3, 0), 0xe1a00003, sub(13, FP, 4), 0xe8bd8800]
    ei = None
    for i in range(p2[0], min(p2[0] + 400, len(words) - len(epi_seq))):
        if words[i:i + len(epi_seq)] == epi_seq:
            ei = i + 1
            break
    if ei is None:
        raise Refuse("routine epilogue not found")
    EPI, AEND = waddr(ei), waddr(ei) - 8
    if branch_target(words[widx(AEND)], AEND) != EPI:
        raise Refuse("unexpected end of block")

    old = words[widx(A):widx(AEND)]
    for need in (ldrb(3, 3, 0x348), ldrb(3, 3, 0x354), ldrb(3, 3, 0x355), str_(3, FP, O_CSP)):
        if need not in old:
            raise Refuse("old block differs from expectation")
    if not any(is_bl(w) and branch_target(w, A + 4 * i) == SET for i, w in enumerate(old)):
        raise Refuse("SetDisplaySettings call not in old block")

    G = None                                          # pointer to the global state struct
    for i in range(len(old) - 3):
        if (old[i] & 0xfff0f000) == 0xe3003000 and (old[i + 1] & 0xfff0f000) == 0xe3403000 \
                and old[i + 2] == ldr(3, 3, 0) and old[i + 3] == ldrb(3, 3, 0x348):
            G = imm16(old[i], old[i + 1])
            break
    if G is None:
        raise Refuse("global state pointer not found")

    GETDEC = None                                     # "mov r0,#0; bl X; str r0,[fp,#-0x20]"
    pre = words[max(p2[0] - 900, 0):p2[0]]
    base_i = max(p2[0] - 900, 0)
    for i in range(len(pre) - 3, -1, -1):
        if pre[i] == mov(0, 0) and is_bl(pre[i + 1]) and pre[i + 2] == str_(0, FP, -0x20):
            GETDEC = branch_target(pre[i + 1], waddr(base_i + i + 1))
            break
    if GETDEC is None:
        raise Refuse("decoder lookup call not found")
    for need in (ldrb(3, FP, -O_ST), ldr(3, FP, -(O_ST - 4)), ldr(3, FP, -(O_ST - 8))):
        if need not in pre:
            raise Refuse("decoder status layout differs")

    bss = [l for l in elf.loads if l["memsz"] > l["filesz"]]
    if len(bss) != 1:
        raise Refuse("bss segment not found")
    bss = bss[0]
    flag = bss["vaddr"] + bss["memsz"] + 5            # one byte behind the end of bss
    if flag & 0xff == 0:
        flag += 1
    new_memsz = flag + 1 - bss["vaddr"]

    a = Asm(A)
    a.e(PUSH_R4); a.e(ldr(3, FP, O_DEPTH)); a.e(mov(2, 0))
    a.e(cmpi(3, 12)); a.e(movw(1, 0x355, EQ)); a.br("b", "HAVE", EQ)
    a.e(cmpi(3, 10)); a.e(movw(1, 0x354, EQ)); a.br("b", "HAVE", EQ)
    a.e(cmpi(3, 8)); a.br("b", "STORE", EQ)
    a.e(mov(3, 0)); a.e(str_(3, FP, O_DEPTH)); a.br("b", "STORE")
    a.label("HAVE"); a.e(movw(0, G & 0xffff)); a.e(movt(0, G >> 16)); a.e(ldr(0, 0, 0))
    a.e(ldrb(3, 0, 0x348)); a.e(cmpi(3, 0)); a.br("b", "STORE", EQ)
    a.e(ldrb_r(3, 0, 1)); a.e(cmpi(3, 0)); a.br("b", "STORE", EQ)
    a.e(ldr(3, FP, O_MODE)); a.e(sub(3, 3, 0x30)); a.e(cmpi(3, 1)); a.e(mov(2, 4, LS))
    a.label("STORE"); a.e(str_(2, FP, O_CSP))
    # kick: once per boot, only while a video is decoding
    a.e(movw(2, flag & 0xffff)); a.e(movt(2, flag >> 16)); a.e(ldrb(3, 2, 0)); a.e(cmpi(3, 0))
    a.br("b", "CMPAPPLY", NE)
    a.e(mov(0, 0)); a.br("bl", GETDEC); a.e(cmpi(0, 0)); a.br("b", "CMPAPPLY", EQ)
    a.e(sub(1, FP, O_ST - 4)); a.e(sub(1, 1, 4)); a.br("bl", GETST)
    a.e(ldrb(3, FP, -O_ST)); a.e(cmpi(3, 0)); a.br("b", "CMPAPPLY", EQ)
    a.e(ldr(3, FP, -(O_ST - 4))); a.e(cmpi(3, 0)); a.br("b", "CMPAPPLY", EQ)
    a.e(ldr(3, FP, -(O_ST - 8))); a.e(cmpi(3, 0)); a.br("b", "CMPAPPLY", EQ)
    a.e(movw(2, flag & 0xffff)); a.e(movt(2, flag >> 16)); a.e(strb(2, 2, 0))
    a.e(ldr(4, FP, O_DEPTH)); a.e(mov(3, 8)); a.e(str_(3, FP, O_DEPTH))
    a.e(sub(0, FP, O_NEW)); a.br("bl", SET); a.e(mov(0, 500)); a.br("bl", SLP); a.e(str_(4, FP, O_DEPTH))
    a.br("b", "APPLY")
    a.label("CMPAPPLY"); a.e(sub(0, FP, O_CUR)); a.e(sub(1, FP, O_NEW)); a.e(mov(2, 0xf8)); a.br("bl", MEMCMP)
    a.e(cmpi(0, 0)); a.br("b", "END", EQ)
    a.label("APPLY"); a.e(sub(0, FP, O_NEW)); a.br("bl", SET)
    a.label("END"); a.e(POP_R4); a.br("b", EPI)
    w = a.done()
    room = (AEND - A) // 4
    if len(w) > room:
        raise Refuse("new code does not fit (%d > %d words)" % (len(w), room))
    w += [NOP] * (room - len(w))

    for i, x in enumerate(words):                      # nothing outside may jump into the block
        at = waddr(i)
        if A <= at < AEND + 4:
            continue
        t = branch_target(x, at)
        if t is not None and A < t < AEND:
            raise Refuse("foreign branch into the block at %#x" % at)

    out = bytearray(data)
    for i, x in enumerate(w):
        struct.pack_into("<I", out, toff + (A - tbase) + 4 * i, x)
    struct.pack_into("<I", out, elf.phoff + bss["idx"] * elf.phentsize + 20, new_memsz)
    return bytes(out), dict(block=A, end=AEND, flag=flag)


def main():
    if len(sys.argv) != 2:
        print(__doc__)
        return 1
    path = sys.argv[1]
    with open(path, "rb") as f:
        data = f.read()
    try:
        out, info = patch(data)
    except Refuse as e:
        # an already patched binary no longer has the old block -> report it clearly
        print("patch_dvb_init: %s - NOT patching %s" % (e, path))
        return 2
    with open(path, "wb") as f:
        f.write(out)
    print("patch_dvb_init: patched %s (block %#x-%#x, flag byte %#x)" % (path, info["block"], info["end"], info["flag"]))
    return 0


if __name__ == "__main__":
    sys.exit(main())
