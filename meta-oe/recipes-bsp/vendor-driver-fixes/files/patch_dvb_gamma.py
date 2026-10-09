#!/usr/bin/env python3
"""Patch the platform-util dvb.ko (gbquad4kpro) so enigma2 can detect HDR10 / HLG.

Enigma2 (lib/dvb/decoder.cpp, eDVBVideo) learns the video gamma / EOTF in two ways:
  * a DVB video event of type 17 (VIDEO_EVENT_GAMMA_CHANGED) whose payload is the gamma
  * /proc/stb/vmpeg/<n>/gamma, read as a hex int when no event has been seen yet
with the values 0 = SDR, 1 = HDR (plain), 2 = SMPTE ST2084 (HDR10), 3 = HLG.

dvb.ko has neither.  It only mirrors what the userspace dvb_init daemon pushes into
/proc/stb/vmpeg/0/eotf (the raw Nexus NEXUS_VideoEotf value of the decoder status:
0 = SDR, 1 = ARIB STD-B67/HLG, 2 = ST2084, 3 = invalid/"HDR"), and that value is never
reported anywhere.  This patch, in place and without changing the file size:

  1. hooks the eotf proc write: it still stores the raw eotf, and additionally maps it to the
     enigma2 numbering (0->0, 1->3, 2->2, 3->1, other->0), keeps the result in the otherwise
     unused pep_split slot and raises the pending-event bit 1<<17 (on every write, so that a
     new channel with the same gamma is reported too).
  2. teaches VIDEO_GET_EVENT to hand out that bit as event type 17 with the gamma as payload
     (the existing poll code already reports POLLPRI for any pending event bit).
  3. turns the pep_split proc entry (nothing but the VideoEnhancement plugin's optional
     "split" mode uses it) into /proc/stb/vmpeg/0/gamma, printing the gamma as "%x\\n".

The new code lives in a run of proc_vmpeg_get_* getters that nothing references.  The script
does not depend on a particular build: it parses the ELF, finds everything through the symbol
table and instruction patterns, derives all offsets from the code, and rewrites the affected
relocations.  If anything does not look as expected it stops with exit status 2 and leaves
the file untouched.  An already patched file is reported and left alone (exit 0).

Usage: patch_dvb_gamma.py [--check] [-o OUT] <path/to/dvb.ko>
  --check   only report what would be done, do not write
  -o OUT    write the patched module to OUT instead of patching in place
Exit status: 0 patched / already patched, 2 unrecognised build (file untouched), 1 usage.

Can be run before or after patch_dvb_acm.py (they touch different code).

Recipe use (same pattern as patch_dvb_acm.py):
    SRC_URI += "file://patch_dvb_gamma.py"
    DEPENDS += "python3-native"
    do_install:append() {
        python3 ${UNPACKDIR}/patch_dvb_gamma.py ${D}/home/root/platform/dvb.ko \\
            || bbwarn "dvb.ko HDR/gamma patch not applied (unrecognised dvb.ko build)"
    }
"""
import hashlib
import struct
import sys

R_ARM_NONE = 0
R_ARM_ABS32 = 2
R_ARM_CALL = 28
R_ARM_JUMP24 = 29

NOP = 0xe1a00000
GAMMA_EVENT = 0x11                  # VIDEO_EVENT_GAMMA_CHANGED
GAMMA_EVENT_BIT = 1 << 17           # pending-event bit chosen for it (bit 16 = progressive)
OLD_NAME = b"\0pep_split\0"
NEW_NAME = b"\0gamma\0\0\0\0\0"


class Refuse(Exception):
    pass


def u32(b, o):
    return struct.unpack_from("<I", b, o)[0]


# ---------------------------------------------------------------- tiny ARM encoder
EQ, HI, AL = 0x0, 0x8, 0xe


def rot_imm(v):
    """Encode v as an ARM modified immediate (rot<<8 | imm8)."""
    for r in range(0, 32, 2):
        x = ((v << r) | (v >> (32 - r))) & 0xffffffff if r else v
        if x < 256:
            return ((r // 2) << 8) | x
    raise Refuse("immediate %#x not encodable" % v)


def dp_imm(cond, op, rn, rd, imm, s=0):
    return (cond << 28) | 0x02000000 | (op << 21) | (s << 20) | (rn << 16) | (rd << 12) | rot_imm(imm)


AND, SUB, ADD, TST, CMP, ORR, MOV, BIC = 0, 2, 4, 8, 10, 12, 13, 14


def ldr(rd, rn, off):
    return 0xe5900000 | (rn << 16) | (rd << 12) | off


def str_(rd, rn, off):
    return 0xe5800000 | (rn << 16) | (rd << 12) | off


def movw(rd, imm):
    return 0xe3000000 | ((imm >> 12) << 16) | (rd << 12) | (imm & 0xfff)


def branch(cond, src, dst):
    off = (dst - (src + 8)) >> 2
    if (dst - (src + 8)) & 3 or not -(1 << 23) <= off < (1 << 23):
        raise Refuse("branch out of range")
    return (cond << 28) | 0x0a000000 | (off & 0xffffff)


def ldr_pc(rd, src, pool):
    off = pool - (src + 8)
    if not 0 <= off < 4096:
        raise Refuse("literal out of range")
    return ldr(rd, 15, off)


R0, R1, R2, R3, R4, R5, R8, IP, SP, LR, PC = 0, 1, 2, 3, 4, 5, 8, 12, 13, 14, 15
PUSH_R3_LR = 0xe92d4008
POP_R3_PC = 0xe8bd8008
BLX_R3 = 0xe12fff33
MOV_IP_R1 = 0xe1a0c001
MOV_R0_0 = 0xe3a00000


# ---------------------------------------------------------------- ELF access
class Elf:
    def __init__(self, d):
        if d[:4] != b"\x7fELF" or d[4] != 1 or d[5] != 1:
            raise Refuse("not a 32-bit little endian ELF")
        if struct.unpack_from("<H", d, 18)[0] != 40:
            raise Refuse("not an ARM object")
        self.d = d
        shoff = u32(d, 32)
        shentsize, shnum, shstrndx = struct.unpack_from("<HHH", d, 46)
        self.sec = [struct.unpack_from("<IIIIIIIIII", d, shoff + i * shentsize) for i in range(shnum)]
        stro = self.sec[shstrndx][4]
        self.byname = {}
        for i, r in enumerate(self.sec):
            s = stro + r[0]
            self.byname[d[s:d.index(b"\0", s)].decode()] = i
        for need in (".text", ".symtab", ".strtab", ".rel.text", ".rodata.str1.1"):
            if need not in self.byname:
                raise Refuse("no " + need)
        self.text_idx = self.byname[".text"]
        self.text_off = self.sec[self.text_idx][4]
        self.text_size = self.sec[self.text_idx][5]
        sym, strtab = self.sec[self.byname[".symtab"]], self.sec[self.byname[".strtab"]]
        self.syms = []                              # (name, value, size, type, shndx)
        for o in range(sym[4], sym[4] + sym[5], 16):
            nm, val, sz, info, _o, shn = struct.unpack_from("<IIIBBH", d, o)
            s = strtab[4] + nm
            self.syms.append((d[s:d.index(b"\0", s)].decode(), val, sz, info & 0xf, shn))
        self.funcs = {}
        for name, val, sz, typ, shn in self.syms:
            if shn == self.text_idx and typ == 2:
                self.funcs.setdefault(name, []).append((val, sz))
        rel = self.sec[self.byname[".rel.text"]]
        self.rel_off = rel[4]
        self.rels = []                              # [file offset of entry, r_offset, sym, type]
        for o in range(rel[4], rel[4] + rel[5], 8):
            r_off, r_info = struct.unpack_from("<II", d, o)
            self.rels.append([o, r_off, r_info >> 8, r_info & 0xff])
        self.rel_at = {}
        for e in self.rels:
            self.rel_at.setdefault(e[1], []).append(e)

    def func(self, name):
        if len(self.funcs.get(name, ())) != 1:
            raise Refuse("symbol %s not found exactly once" % name)
        return self.funcs[name][0]

    def text_word(self, addr):
        return u32(self.d, self.text_off + addr)

    def words(self, addr, size):
        return [self.text_word(addr + 4 * i) for i in range(size // 4)]

    def reloc_here(self, addr):
        r = self.rel_at.get(addr, [])
        if len(r) != 1:
            raise Refuse("no unique relocation at .text+%#x" % addr)
        return r[0]

    def sym_is_section(self, idx, name):
        n, _v, _s, typ, shn = self.syms[idx]
        return typ == 3 and isinstance(shn, int) and shn < len(self.sec) and \
            self.sec_name(shn) == name

    def sec_name(self, shn):
        for k, v in self.byname.items():
            if v == shn:
                return k
        return None


def derive(e):
    """Look up everything the patch depends on and validate it against the expected code."""
    g = {}
    # per-video record offsets, from the (dead) getters/setters
    ga, gsz = e.func("proc_vmpeg_get_eotf")
    w = e.words(ga, gsz)
    # ldr r3,[pc,#x]; mov r2,#0xac; movw ip,#eotf; mla r0,r2,r0,r3; ldr r3,[r0,ip]; mov r0,#0;
    # str r3,[r1]; bx lr; <pool: G>
    if len(w) != 9 or w[0] & 0xfffff000 != 0xe59f3000 or w[1] != 0xe3a020ac or w[2] & 0xfff0f000 != 0xe300c000             or w[3] != 0xe0203092 or w[4] != 0xe790300c or w[7] != 0xe12fff1e:
        raise Refuse("proc_vmpeg_get_eotf not recognised")
    eotf_off = ((w[2] >> 4) & 0xf000) | (w[2] & 0xfff)
    sa, ssz = e.func("proc_vmpeg_set_pep_split")
    w = e.words(sa, ssz)
    if len(w) != 8 or w[1] != 0xe3a020ac or w[2] & 0xfff0f000 != 0xe300c000 or w[3] != 0xe0203092             or w[4] != 0xe780100c:                                  # str r1,[r0,ip]
        raise Refuse("proc_vmpeg_set_pep_split not recognised")
    slot_off = ((w[2] >> 4) & 0xf000) | (w[2] & 0xfff)
    if slot_off & 3 or slot_off == eotf_off:
        raise Refuse("implausible record offsets")
    # pending-event word of the 0x1e0-byte state record, from the progressive setter:
    # ldrne ip,[r3,#ev]; orrne ip,ip,#0x10000; strne ip,[r3,#ev]
    pa, psz = e.func("proc_vmpeg_set_progressive")
    w = e.words(pa, psz)
    ev = w[6] & 0xfff if len(w) > 8 else None
    if ev is None or w[1] != 0xe3a03e1e or w[6] != 0x1593c000 | ev or w[7] != 0x138cc801             or w[8] != 0x1583c000 | ev:
        raise Refuse("proc_vmpeg_set_progressive not recognised")
    g.update(eotf_off=eotf_off, slot_off=slot_off, ev_off=ev)

    # global record base G: relocation of the pool word of the getter
    rg = e.reloc_here(ga + 4 * 8)
    g["G_sym"], g["G_word"] = rg[2], e.text_word(ga + 4 * 8)
    if rg[3] != R_ARM_ABS32:
        raise Refuse("unexpected relocation type for G")
    return g


def patch(data):
    e = Elf(data)

    # ---------------- already patched?
    sh_a, sh_sz = e.func("proc_stb_vmpeg_0_pep_split_show")
    ro = e.sec[e.byname[".rodata.str1.1"]]
    rodata = data[ro[4]:ro[4] + ro[5]]
    if (e.text_word(sh_a) >> 24) == 0xea and rodata.count(NEW_NAME) == 1 and rodata.count(OLD_NAME) == 0:
        return None, dict(already=True)

    g = derive(e)
    out = bytearray(data)

    # ---------------- the rename target
    if rodata.count(OLD_NAME) != 1 or rodata.count(NEW_NAME):
        raise Refuse("pep_split name string not found exactly once")
    name_pos = ro[4] + rodata.index(OLD_NAME)

    # ---------------- the pep_split show routine (becomes a branch into the cave)
    sw = e.words(sh_a, sh_sz)
    if sh_sz != 40 or sw[0] != PUSH_R3_LR or sw[4] != BLX_R3 or sw[6] != POP_R3_PC:
        raise Refuse("proc_stb_vmpeg_0_pep_split_show not recognised")
    pool = [e.reloc_here(sh_a + 28 + 4 * i) for i in range(3)]      # fmt, "off" string, seq_printf
    if [p[3] for p in pool] != [R_ARM_ABS32] * 3:
        raise Refuse("unexpected pep_split_show relocations")
    seq_sym = pool[2][2]
    if e.syms[seq_sym][0] != "seq_printf":
        raise Refuse("pep_split_show does not call seq_printf")

    # ---------------- eotf_write: the tail that stores the raw value
    ea, esz = e.func("proc_stb_vmpeg_0_eotf_write")
    ew = e.words(ea, esz)
    movw_eotf = movw(R3, g["eotf_off"])
    at = None
    for i in range(1, len(ew) - 5):
        if ew[i] == movw_eotf and ew[i - 1] & 0xfffff000 == 0xe59f2000 and ew[i + 1] == 0xe1a00004 \
                and ew[i + 2] == ldr(R1, SP, 4) and ew[i + 3] == 0xe7821003 \
                and ew[i + 4] == dp_imm(AL, ADD, SP, SP, 0xc) and ew[i + 5] == 0xe8bd8030:
            at = i
    if at is None:
        raise Refuse("proc_stb_vmpeg_0_eotf_write tail not recognised")
    ldr_g = ea + 4 * (at - 1)
    pool_g = ldr_g + 8 + (ew[at - 1] & 0xfff)
    rg = e.reloc_here(pool_g)
    if (rg[2], e.text_word(pool_g)) != (g["G_sym"], g["G_word"]):
        raise Refuse("eotf_write does not load the record base")
    hook_at = ea + 4 * (at + 2)                 # the 'ldr r1,[sp,#4]'
    epilogue = ea + 4 * (at + 4)                # 'add sp,sp,#0xc'
    # the "%x\n" format used by sscanf in eotf_write: last-but-two pool word
    fmt_addr = ea + esz - 12
    fmt_rel = e.reloc_here(fmt_addr)
    if not e.sym_is_section(fmt_rel[2], ".rodata.str1.1"):
        raise Refuse("eotf_write format pool word not found")
    fmt_word = e.text_word(fmt_addr)
    fpos = ro[4] + fmt_word
    if data[fpos:fpos + 4] != b"%x\n\0":
        raise Refuse("eotf_write format is not '%x\\n'")

    # ---------------- VIDEO_GET_EVENT in dev_video_ioctl
    ia, isz = e.func("dev_video_ioctl")
    iw = e.words(ia, isz)
    hit = None
    for i in range(len(iw) - 9):
        if iw[i] == dp_imm(AL, TST, R3, 0, 0x10000, 1) and iw[i + 1] >> 24 == 0x0a and \
                iw[i + 2] == dp_imm(AL, BIC, R3, R3, 0x10000) and iw[i + 3] == str_(R3, R5, 0xb0) and \
                iw[i + 4] == dp_imm(AL, MOV, 0, R3, 0x10) and iw[i + 5] == str_(R3, R4, 0) and \
                iw[i + 6] == ldr(R3, R5, 0x98) and iw[i + 7] == str_(R3, R4, 8) and iw[i + 8] >> 24 == 0xea:
            hit = i
    if hit is None:
        raise Refuse("VIDEO_GET_EVENT progressive branch not recognised")
    beq_at = ia + 4 * (hit + 1)
    common_str = ia + 4 * (hit + 7)             # 'str r3,[r4,#8]; b done'
    done = beq_at + 8 + ((((iw[hit + 1] & 0xffffff) ^ 0x800000) - 0x800000) << 2)
    # the pending word is state(r5)+0xb0 == record(dev)+ev_off; r8 must be G in this function
    if iw[0] != 0xe92d41f3 or iw[4] & 0xfffff000 != 0xe59f8000:    # push {r0,r1,r4-r8,lr}; ldr r8,[pc,#x]
        raise Refuse("dev_video_ioctl prologue not recognised")
    pool8 = ia + 16 + 8 + (iw[4] & 0xfff)
    r8 = e.reloc_here(pool8)
    if (r8[2], e.text_word(pool8)) != (g["G_sym"], g["G_word"]):
        raise Refuse("dev_video_ioctl: r8 is not the record base")
    # the ioctl addresses the pending word as state+0xb0 with state = record + 0x2d8 + 4
    if g["ev_off"] != 0xb0 + 0x2dc or dp_imm(AL, ADD, 6, 6, 0x2d8) not in iw[:16]             or dp_imm(AL, ADD, 6, 5, 4) not in iw[:16]:
        raise Refuse("pending-event offsets disagree")

    # ---------------- the dead run that receives the new code
    names = ["proc_vmpeg_get_aspect", "proc_vmpeg_get_codec", "proc_vmpeg_get_colordepth",
             "proc_vmpeg_get_eotf", "proc_vmpeg_get_framerate", "proc_vmpeg_get_progressive",
             "proc_vmpeg_get_visible", "proc_vmpeg_get_zorder", "proc_vmpeg_get_xres",
             "proc_vmpeg_get_yres"]
    cave, cave_end = e.func(names[0])[0], None
    pos = cave
    for n in names:
        a, s = e.func(n)
        if a != pos or s != 36:
            raise Refuse("getter run is not contiguous at " + n)
        pos += s
    cave_end = pos
    check_dead(e, cave, cave_end)

    # ================================================================ layout
    CAVE = cave
    C2 = CAVE                                   # ioctl: gamma event
    C1 = C2 + 4 * 9                             # eotf_write: map + flag
    SH = C1 + 4 * 14                            # show routine
    P_FMT, P_ADDR, P_SEQ = SH + 4 * 8, SH + 4 * 9, SH + 4 * 10
    if P_SEQ + 4 > cave_end:
        raise Refuse("cave too small")

    hi, lo = g["slot_off"] & ~0xfff, g["slot_off"] & 0xfff
    code = {}

    # C2: reached from the 'beq' that skips the progressive event
    code[C2 + 0x00] = dp_imm(AL, TST, R3, 0, GAMMA_EVENT_BIT, 1)
    code[C2 + 0x04] = branch(EQ, C2 + 0x04, done)
    code[C2 + 0x08] = dp_imm(AL, BIC, R3, R3, GAMMA_EVENT_BIT)
    code[C2 + 0x0c] = str_(R3, R5, 0xb0)
    code[C2 + 0x10] = dp_imm(AL, MOV, 0, R3, GAMMA_EVENT)
    code[C2 + 0x14] = str_(R3, R4, 0)
    code[C2 + 0x18] = dp_imm(AL, ADD, R8, R3, hi)
    code[C2 + 0x1c] = ldr(R3, R3, lo)
    code[C2 + 0x20] = branch(AL, C2 + 0x20, common_str)
    code[beq_at] = branch(EQ, beq_at, C2)

    # C1: r2 = G, r3 = eotf offset, [sp,#4] = value, r0 = return value (all live from eotf_write)
    k = C1
    seq1 = [
        ldr(R1, SP, 4),
        0xe7821003,                                         # str r1,[r2,r3]       raw eotf
        MOV_IP_R1,
        dp_imm(AL, CMP, IP, 0, 1, 1),
        dp_imm(EQ, MOV, 0, R1, 3),                          # HLG       -> 3
        dp_imm(AL, CMP, IP, 0, 3, 1),
        dp_imm(EQ, MOV, 0, R1, 1),                          # "HDR"     -> 1
        dp_imm(HI, MOV, 0, R1, 0),                          # unknown   -> 0
        dp_imm(AL, ADD, R2, R3, hi),
        str_(R1, R3, lo),                                   # gamma slot
        ldr(IP, R2, g["ev_off"]),                           # raise the event on every write: dvb_init writes
        dp_imm(AL, ORR, IP, IP, GAMMA_EVENT_BIT),           # it at each stream start, and a new HDR10 channel
        str_(IP, R2, g["ev_off"]),                          # after an HDR10 channel must still be reported
        None,                                               # b epilogue
    ]
    for x in seq1:
        if x is None:
            x = branch(AL, k, epilogue)
        code[k] = x
        k += 4
    code[hook_at] = branch(AL, hook_at, C1)

    # show routine
    sh = [
        PUSH_R3_LR,
        ldr_pc(R1, SH + 4, P_FMT),
        ldr_pc(R2, SH + 8, P_ADDR),
        ldr(R2, R2, 0),
        ldr_pc(R3, SH + 16, P_SEQ),
        BLX_R3,
        MOV_R0_0,
        POP_R3_PC,
    ]
    for i, x in enumerate(sh):
        code[SH + 4 * i] = x

    # ================================================================ write
    def put(addr, word):
        struct.pack_into("<I", out, e.text_off + addr, word)

    # relocations of the dead run are no longer wanted
    for r in e.rels:
        if cave <= r[1] < cave_end:
            struct.pack_into("<I", out, r[0] + 4, (r[2] << 8) | R_ARM_NONE)
    # the three pool words of the show routine: move the relocations of the old one over
    fmt_e, str_e, seq_e = pool
    moves = [(fmt_e, P_FMT, fmt_rel[2], fmt_word),                  # "%x\n"
             (str_e, P_ADDR, g["G_sym"], g["G_word"] + g["slot_off"]),     # &gamma slot
             (seq_e, P_SEQ, seq_sym, 0)]                            # seq_printf
    for ent, new_at, sym, word in moves:
        struct.pack_into("<II", out, ent[0], new_at, (sym << 8) | R_ARM_ABS32)
        put(new_at, word)
    for addr, word in code.items():
        put(addr, word)
    # the old show routine just jumps there
    put(sh_a, branch(AL, sh_a, SH))
    for i in range(1, sh_sz // 4):
        put(sh_a + 4 * i, NOP)
    out[name_pos:name_pos + len(NEW_NAME)] = NEW_NAME
    if rodata.count(OLD_NAME) != 1:                                 # paranoia
        raise Refuse("name string changed")
    return bytes(out), dict(already=False, cave=cave, c1=C1, c2=C2, show=SH, hook=hook_at,
                            beq=beq_at, g=g)


def check_dead(e, lo, hi):
    """Refuse if anything outside [lo,hi) can reach the run."""
    d = e.d
    inside = lambda a: lo <= a < hi
    tbase = e.text_off
    # direct b/bl
    for a in range(0, e.text_size - 3, 4):
        w = u32(d, tbase + a)
        if (w & 0x0e000000) == 0x0a000000 and (w >> 28) != 0xf and not inside(a):
            off = w & 0xffffff
            if off & 0x800000:
                off -= 1 << 24
            if inside(a + 8 + off * 4):
                raise Refuse("code at .text+%#x branches into the cave" % a)
    # relocations against the getters, or section-relative ones landing inside
    for ent in e.rels:
        _o, r_off, sym, typ = ent
        if inside(r_off) and typ != R_ARM_ABS32:
            raise Refuse("unexpected relocation type inside the cave")
    names = {n for n, v in e.funcs.items() for (a, _s) in v if inside(a)}
    for i in range(len(e.sec)):
        s = e.sec[i]
        if s[1] != 9 or s[0] == 0:                          # SHT_REL
            continue
        for o in range(s[4], s[4] + s[5], 8):
            r_off, r_info = struct.unpack_from("<II", d, o)
            sym, typ = r_info >> 8, r_info & 0xff
            if i == e.byname[".rel.text"] and inside(r_off):
                continue
            name, val, _sz, st, shn = e.syms[sym]
            tgt_sec = e.sec[s[7]] if s[7] < len(e.sec) else None
            if st == 2 and name in names:
                raise Refuse("%s is referenced by a relocation" % name)
            if st == 3 and shn == e.text_idx and tgt_sec is not None:
                loc = tgt_sec[4] + r_off
                if typ == R_ARM_ABS32 and inside(u32(d, loc)):
                    raise Refuse("data word at section+%#x points into the cave" % r_off)
                if typ in (R_ARM_CALL, R_ARM_JUMP24):
                    w = u32(d, loc)
                    off = w & 0xffffff
                    if off & 0x800000:
                        off -= 1 << 24
                    if inside(off * 4 + 8):
                        raise Refuse("call relocation points into the cave")


def main():
    args = sys.argv[1:]
    check, outpath = False, None
    while args and args[0].startswith("-"):
        if args[0] == "--check":
            check, args = True, args[1:]
        elif args[0] == "-o" and len(args) > 1:
            outpath, args = args[1], args[2:]
        else:
            print(__doc__)
            return 1
    if len(args) != 1:
        print(__doc__)
        return 1
    path = args[0]
    with open(path, "rb") as f:
        data = f.read()
    try:
        out, info = patch(data)
    except Refuse as ex:
        print("patch_dvb_gamma: %s - NOT patching %s" % (ex, path))
        return 2
    if info["already"]:
        print("patch_dvb_gamma: %s is already patched, nothing to do" % path)
        return 0
    desc = "cave .text+%#x (event %#x, eotf hook %#x, show %#x)" % (
        info["cave"], info["c2"], info["c1"], info["show"])
    if check:
        print("patch_dvb_gamma: %s can be patched, %s; not writing (--check)" % (path, desc))
        return 0
    assert len(out) == len(data)
    with open(outpath or path, "wb") as f:
        f.write(out)
    print("patch_dvb_gamma: patched %s -> %s, %s, sha256 %s"
          % (path, outpath or path, desc, hashlib.sha256(out).hexdigest()))
    return 0


if __name__ == "__main__":
    sys.exit(main())
