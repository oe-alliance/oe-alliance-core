#!/usr/bin/env python3
"""Patch the VU+ Ultimo4K (BCM7444, Nexus 17.1, kernel 3.14.28) dvb-bcm7444.ko so enigma2 can
detect HDR10 / HLG.

Enigma2 (lib/dvb/decoder.cpp, eDVBVideo) learns the video gamma in two ways:
  * a DVB video event of type 17 (VIDEO_EVENT_GAMMA_CHANGED) whose payload is the gamma
  * /proc/stb/vmpeg/<n>/gamma, read as a hex int when no event has been seen yet
with the values 0 = SDR, 1 = HDR (plain), 2 = SMPTE ST2084 (HDR10), 3 = HLG.

dvb-bcm7444.ko is an in-kernel Nexus client.  Its streamChangedCallback already copies the
decoder's NEXUS_VideoDecoderStreamInformation (0x84 bytes, eotf at +0x4c: 0 = SDR, 1 = HLG,
2 = ST2084) into a global, but nothing reports it.  This patch, in place and without changing
the file size:

  1. streamChangedCallback: for every valid stream information report, and when the eotf differs from the stored one
     (a stopped decoder reports eotf 0, so zapping to SDR is reported at once) it sets bit 0 and bit 1 of poll_pri, the flag bcm7335_video_poll turns into POLLPRI, and wakes the
     poll wait queue (otherwise enigma2 only notices at the next unrelated wake-up, e.g. a zap).
  2. VIDEO_GET_EVENT: the branch that reports "nothing changed" (dummy event 0x4d) now emits event
     type 17 with the gamma (eotf mapped 0->0, 1->3, 2->2, 3->1) when bit 1 is set, and the
     clearing of poll_pri keeps bit 1 so no gamma change is lost behind a size/rate event.
  3. the pep_split proc entry (only the VideoEnhancement plugin's optional "split" mode uses it)
     becomes /proc/stb/vmpeg/0/gamma, a read-only file that prints the gamma as "%x\\n".

The new code lives in a run of unreferenced functions.  The script does not depend on a
particular build: it parses the ELF, finds everything through the symbol table and instruction
patterns, and rewrites the affected relocations.  If anything does not look as expected it
stops with exit status 2 and leaves the file untouched.  An already patched file is reported
and left alone (exit 0).

Usage: patch_dvb_bcm7444_gamma.py [--check] [--debug] [-o OUT] <path/to/dvb-bcm7444.ko>
  --debug   additionally printk 'gm:...' trace lines (dmesg) from every step of the gamma event path
Exit status: 0 patched / already patched, 2 unrecognised build (file untouched), 1 usage.
"""
import hashlib
import struct
import sys

R_ARM_NONE, R_ARM_ABS32, R_ARM_CALL, R_ARM_JUMP24 = 0, 2, 28, 29

NOP = 0xe1a00000
GAMMA_EVENT = 0x11
EOTF_OFF = 0x4c                     # NEXUS_VideoDecoderStreamInformation.eotf (stream info is 0x84 bytes)
STREAMINFO_SIZE = 0x84
OLD_NAME = b"\0pep_split\0"
NEW_NAME = b"\0gamma\0\0\0\0\0"


class Refuse(Exception):
    pass


def u32(b, o):
    return struct.unpack_from("<I", b, o)[0]


# ---------------------------------------------------------------- tiny ARM encoder
EQ, NE, LO, HI, AL = 0x0, 0x1, 0x3, 0x8, 0xe
AND, SUB, ADD, TST, CMP, ORR, MOV, BIC, MVN = 0, 2, 4, 8, 10, 12, 13, 14, 15
R0, R1, R2, R3, R4, R5, R6, IP, SP, LR, PC = 0, 1, 2, 3, 4, 5, 6, 12, 13, 14, 15


def rot_imm(v):
    for r in range(0, 32, 2):
        x = ((v << r) | (v >> (32 - r))) & 0xffffffff if r else v
        if x < 256:
            return ((r // 2) << 8) | x
    raise Refuse("immediate %#x not encodable" % v)


def dp_imm(cond, op, rn, rd, imm, s=0):
    return (cond << 28) | 0x02000000 | (op << 21) | (s << 20) | (rn << 16) | (rd << 12) | rot_imm(imm)


def dp_reg(cond, op, rn, rd, rm, s=0):
    return (cond << 28) | (op << 21) | (s << 20) | (rn << 16) | (rd << 12) | rm


def ldr(rd, rn, off):
    return 0xe5900000 | (rn << 16) | (rd << 12) | off


def ldrb(rd, rn, off):
    return 0xe5d00000 | (rn << 16) | (rd << 12) | off


def str_(rd, rn, off):
    return 0xe5800000 | (rn << 16) | (rd << 12) | off


def branch(cond, src, dst, link=False):
    d = dst - (src + 8)
    if d & 3 or not -(1 << 25) <= d < (1 << 25):
        raise Refuse("branch out of range")
    return (cond << 28) | (0x0b000000 if link else 0x0a000000) | ((d >> 2) & 0xffffff)


def ldr_pc(rd, src, pool):
    off = pool - (src + 8)
    if not 0 <= off < 4096:
        raise Refuse("literal out of range")
    return ldr(rd, PC, off)


PUSH_R4_R5_LR = 0xe92d4030
POP_R4_R5_PC = 0xe8bd8030
STRH_R1_SP = 0xe1cd10b0


class Asm:
    """Collects words and labels, resolves pc-relative items once the base address is known."""

    def __init__(self):
        self.items, self.labels = [], {}

    def label(self, name):
        self.labels[name] = len(self.items)

    def w(self, word):
        self.items.append(word)

    def f(self, fn):                      # fn(addr, labels) -> word
        self.items.append(fn)

    def size(self):
        return 4 * len(self.items)

    def assemble(self, base):
        lab = {k: base + 4 * v for k, v in self.labels.items()}
        out = []
        for i, it in enumerate(self.items):
            out.append(it(base + 4 * i, lab) if callable(it) else it)
        return out, lab


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
        for need in (".text", ".symtab", ".strtab", ".rel.text", ".rel.data", ".data", ".rodata.str1.1"):
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
        self.undef = {}
        for i, (name, _v, _s, _t, shn) in enumerate(self.syms):
            if shn == 0 and name:
                self.undef[name] = i
        self.text_secsym = [i for i, s in enumerate(self.syms) if s[3] == 3 and s[4] == self.text_idx]
        self.rels = self._rels(".rel.text")
        self.drels = self._rels(".rel.data")
        self.rel_at = {}
        for e in self.rels:
            self.rel_at.setdefault(e[1], []).append(e)

    def _rels(self, name):
        s = self.sec[self.byname[name]]
        out = []
        for o in range(s[4], s[4] + s[5], 8):
            r_off, r_info = struct.unpack_from("<II", self.d, o)
            out.append([o, r_off, r_info >> 8, r_info & 0xff])
        return out

    def func(self, name):
        if len(self.funcs.get(name, ())) != 1:
            raise Refuse("symbol %s not found exactly once" % name)
        return self.funcs[name][0]

    def tw(self, addr):
        return u32(self.d, self.text_off + addr)

    def words(self, addr, size):
        return [self.tw(addr + 4 * i) for i in range(size // 4)]

    def reloc_here(self, addr):
        r = self.rel_at.get(addr, [])
        if len(r) != 1:
            raise Refuse("no unique relocation at .text+%#x" % addr)
        return r[0]

    def reloc_name(self, addr):
        r = self.rel_at.get(addr)
        return self.syms[r[0][2]][0] if r and len(r) == 1 else None


def dead_runs(e):
    """Contiguous runs of functions that nothing calls, branches to, or takes the address of."""
    d, tb = e.d, e.text_off
    called = set()
    for a in range(0, e.text_size - 3, 4):
        w = u32(d, tb + a)
        if (w & 0x0e000000) == 0x0a000000 and (w >> 28) != 0xf:
            off = w & 0xffffff
            if off & 0x800000:
                off -= 1 << 24
            called.add(a + 8 + off * 4)
    refd = set()
    for i in range(len(e.sec)):
        s = e.sec[i]
        if s[1] != 9:
            continue
        tgt = e.sec[s[7]] if s[7] < len(e.sec) else None
        for o in range(s[4], s[4] + s[5], 8):
            r_off, r_info = struct.unpack_from("<II", d, o)
            sym, typ = r_info >> 8, r_info & 0xff
            name, val, _sz, st, shn = e.syms[sym]
            if st == 2 and shn == e.text_idx:
                refd.add(val)
            elif st == 3 and shn == e.text_idx and tgt is not None:
                w = u32(d, tgt[4] + r_off)
                if typ == R_ARM_ABS32:
                    refd.add(w)
                elif typ in (R_ARM_CALL, R_ARM_JUMP24):
                    off = w & 0xffffff
                    if off & 0x800000:
                        off -= 1 << 24
                    refd.add(off * 4 + 8)
    funcs = sorted((v, sz, n) for n, vs in e.funcs.items() for (v, sz) in vs)
    runs, run = [], []
    for a, sz, n in funcs:
        if a in called or a in refd or sz == 0 or a % 4 or sz % 4:
            if run:
                runs.append(run)
            run = []
            continue
        if run and run[-1][0] + run[-1][1] != a:
            runs.append(run)
            run = []
        run.append((a, sz, n))
    if run:
        runs.append(run)
    return runs


def patch(data, debug=False):
    e = Elf(data)
    ro = e.sec[e.byname[".rodata.str1.1"]]
    rodata = data[ro[4]:ro[4] + ro[5]]
    e.func("bcm7335_video_ioctl")                               # not a dvb-bcm7444.ko -> refuse
    if rodata.count(NEW_NAME) == 1 and rodata.count(OLD_NAME) == 0:
        return None, dict(already=True)
    if rodata.count(OLD_NAME) != 1:
        raise Refuse("pep_split name string not found exactly once")
    name_off = rodata.index(OLD_NAME) + 1                      # offset of "pep_split" in the section

    # ---------------- poll_pri / stream info globals
    sa, ssz = e.func("bcm7335_source_changed")
    sw = e.words(sa, ssz)
    poll_off, hook_sc = None, None
    for i in range(len(sw) - 2):
        if sw[i] == dp_imm(AL, MOV, 0, R2, 1) and sw[i + 1] & 0xfffff000 == 0xe5832000:      # mov r2,#1; str r2,[r3,#x]
            poll_off = sw[i + 1] & 0xfff
            hook_sc = sa + 4 * (i + 1)
    g_pool = None
    for i, w in enumerate(sw):
        if w & 0xfffff000 == 0xe59f3000:
            g_pool = sa + 4 * i + 8 + (w & 0xfff)
            break
    if poll_off is None or g_pool is None or hook_sc in e.rel_at:
        raise Refuse("bcm7335_source_changed not recognised")
    g_rel = e.reloc_here(g_pool)
    G = (g_rel[2], e.tw(g_pool))

    ca, csz = e.func("streamChangedCallback")
    cw = e.words(ca, csz)
    gsi = e.undef.get("NEXUS_VideoDecoder_GetStreamInformation")
    memcpy = [i for i, sy in enumerate(e.syms) if sy[0] == "NEXUS_BKNI_Memcpy"]
    memcpy = memcpy[0] if len(memcpy) == 1 else None
    if gsi is None or memcpy is None:
        raise Refuse("stream information imports missing")
    # add r1,sp,#IMM ; bl GetStreamInformation
    imm = None
    for i in range(2, len(cw)):
        r = e.rel_at.get(ca + 4 * i)
        if r and len(r) == 1 and r[0][2] == gsi and cw[i - 2] & 0xfffff000 == 0xe28d1000:
            imm = cw[i - 2]
    if imm is None:
        raise Refuse("streamChangedCallback: GetStreamInformation call not recognised")
    hook = None
    for i in range(len(cw) - 4):
        r = e.rel_at.get(ca + 4 * (i + 3))
        if cw[i] & 0xfffff000 == 0xe59f0000 and cw[i + 1] == imm and cw[i + 2] == dp_imm(AL, MOV, 0, R2, STREAMINFO_SIZE) \
                and r and len(r) == 1 and r[0][2] == memcpy:
            hook = i
    if hook is None:
        raise Refuse("streamChangedCallback: stream information copy not recognised")
    hook_s = ca + 4 * hook
    back_s = hook_s + 4
    g2_pool = hook_s + 8 + (cw[hook] & 0xfff)
    g2_rel = e.reloc_here(g2_pool)
    G2 = (g2_rel[2], e.tw(g2_pool))
    new_eotf_sp = (imm & 0xff) + EOTF_OFF if (imm & 0xf00) == 0 else None      # the stack buffer is at sp+IMM
    if new_eotf_sp is None or new_eotf_sp > 0xfff:
        raise Refuse("unexpected stack buffer offset")
    if G[0] != G2[0]:
        raise Refuse("globals live in different sections")

    # ---------------- the poll wait queue: bcm7335_dec_fifo_empty wakes it with __wake_up(&wq, 3, 1, 0)
    fa, fsz = e.func("bcm7335_dec_fifo_empty")
    fw = e.words(fa, fsz)
    if len(fw) < 11 or fw[4] & 0xfffff000 != 0xe59f0000 or fw[5] != dp_imm(AL, MOV, 0, R1, 3)             or fw[6] != dp_imm(AL, MOV, 0, R2, 1) or fw[7] != dp_imm(AL, MOV, 0, R3, 0) or fw[8] >> 24 != 0xea:
        raise Refuse("bcm7335_dec_fifo_empty not recognised")
    wk = e.reloc_here(fa + 4 * 8)
    if e.syms[wk[2]][0] != "__wake_up" or wk[3] != R_ARM_JUMP24:
        raise Refuse("bcm7335_dec_fifo_empty does not tail-call __wake_up")
    wq_pool = fa + 16 + 8 + (fw[4] & 0xfff)
    WQ = (e.reloc_here(wq_pool)[2], e.tw(wq_pool))
    wake_sym = wk[2]

    # ---------------- VIDEO_GET_EVENT in bcm7335_video_ioctl
    ia, isz = e.func("bcm7335_video_ioctl")
    iw = e.words(ia, isz)
    e_hits, c_hits = [], []
    for i in range(len(iw) - 3):
        if iw[i] == dp_imm(AL, MOV, 0, R2, 0x4d) and iw[i + 1] & 0xfffff000 == 0xe58d2000:     # mov r2,#0x4d; str r2,[sp,#T]
            e_hits.append(i)
        if iw[i] == dp_imm(AL, MOV, 0, R5, 0) and iw[i + 1] == str_(R5, R3, poll_off) and iw[i + 2] >> 24 == 0xea:
            c_hits.append(i)
    if len(e_hits) != 1 or len(c_hits) != 1:
        raise Refuse("VIDEO_GET_EVENT blocks not recognised (%d/%d)" % (len(e_hits), len(c_hits)))
    ei, ci = e_hits[0], c_hits[0]
    tslot = iw[ei + 1] & 0xfff
    uslot = tslot + 8
    if str_(R2, SP, uslot) not in iw and str_(R1, SP, uslot) not in iw:       # progressive/size events store u there
        raise Refuse("event payload slot not recognised")
    hook_e = ia + 4 * ei
    back_e = hook_e + 4                                         # the 'str r2,[sp,#T]'
    hook_c = ia + 4 * (ci + 1)                                  # the 'str r5,[r3,#poll]'
    cw_exit = hook_c + 4
    exit_c = cw_exit + 8 + ((((iw[ci + 2] & 0xffffff) ^ 0x800000) - 0x800000) << 2)
    for a in (hook_s, hook_e, hook_c):
        if a in e.rel_at:
            raise Refuse("hook site carries a relocation")

    # ---------------- the proc entry
    entry = None
    for r in e.drels:
        if r[3] == R_ARM_ABS32 and e.syms[r[2]][3] == 3 and e.sec[e.syms[r[2]][4]][4] == ro[4] \
                and u32(data, e.sec[e.byname[".data"]][4] + r[1]) == name_off:
            entry = r
    if entry is None:
        raise Refuse("proc entry for pep_split not found")
    rd = {r[1]: r for r in e.drels}
    read_rel, write_rel = rd.get(entry[1] + 4), rd.get(entry[1] + 8)
    if read_rel is None or write_rel is None or e.syms[read_rel[2]][0] != "proc_read" \
            or e.syms[write_rel[2]][0] != "proc_write_vmpeg_pep_split":
        raise Refuse("pep_split proc entry layout not recognised")
    # a read-only entry as template: the 'progressive' reader keeps the generic proc_write
    wr_sym = None
    for r in e.drels:
        if e.syms[r[2]][0] == "proc_read_vmpeg_progressive":
            nxt = rd.get(r[1] + 4)
            if nxt is not None and e.syms[nxt[2]][0] == "proc_write":
                wr_sym = nxt[2]
    if wr_sym is None or not e.text_secsym or "__copy_to_user" not in e.undef:
        raise Refuse("proc_write / .text symbol / __copy_to_user not found")
    copy_sym = e.undef["__copy_to_user"]

    # ---------------- code
    def gamma_from_eotf(a, v, tmp):
        """v holds the eotf; leaves the enigma2 gamma in v (tmp is clobbered)."""
        a.w(0xe1a00000 | (tmp << 12) | v)                          # mov tmp,v
        a.w(dp_imm(AL, CMP, tmp, 0, 1, 1))
        a.w(dp_imm(EQ, MOV, 0, v, 3))
        a.w(dp_imm(AL, CMP, tmp, 0, 3, 1))
        a.w(dp_imm(EQ, MOV, 0, v, 1))
        a.w(dp_imm(HI, MOV, 0, v, 0))

    strings = []
    PUSH_ALL, POP_ALL = 0xe92d500f, 0xe8bd500f                  # push/pop {r0-r3,ip,lr}
    if debug and "printk" not in e.undef:
        raise Refuse("printk not imported")

    def trace(A, tag, text, setup):
        """printk(text, r1, r2, r3) with every caller-saved register preserved; setup(adj) loads r1..r3
        (adj = bytes the push moved sp by, for sp-relative loads)."""
        if not debug:
            return
        strings.append((tag, text.encode() + b"\0"))
        A.w(PUSH_ALL)
        setup(24)
        A.f(lambda a, L: dp_imm(AL, ADD, PC, R0, L["STR_" + tag] - (a + 8)))
        A.label("CALLP_" + tag)
        A.w(0xebfffffe)                                          # bl printk (relocated)
        A.w(POP_ALL)

    A = Asm()
    # -- source_changed: OR instead of overwrite, so a pending gamma bit survives
    A.label("SC")
    trace(A, "SC", "gm:SC pri=%x\n", lambda adj: A.w(ldr(R1, R3, poll_off)))
    A.w(ldr(R1, R3, poll_off))
    A.w(dp_imm(AL, ORR, R1, R1, 1))
    A.w(str_(R1, R3, poll_off))
    A.f(lambda a, L: branch(AL, a, hook_sc + 4))
    # -- event (reached from the dummy-event spot)
    A.label("E")
    A.w(ldr(R2, R3, poll_off))
    A.w(dp_imm(AL, TST, R2, 0, 2, 1))
    A.f(lambda a, L: branch(NE, a, L["EG"]))
    trace(A, "D", "gm:D pri=%x\n", lambda adj: A.w(0xe1a01002))     # mov r1,r2 (poll_pri)
    A.w(dp_imm(AL, MOV, 0, R2, 0x4d))
    A.f(lambda a, L: branch(AL, a, back_e))
    A.label("EG")
    A.w(dp_imm(AL, BIC, R2, R2, 2))
    A.w(str_(R2, R3, poll_off))
    def e_setup(adj):
        A.f(lambda a, L: ldr_pc(R1, a, L["PG2"]))
        A.w(ldr(R1, R1, EOTF_OFF))
        A.w(ldr(R2, R3, poll_off))
    trace(A, "E", "gm:E eotf=%d pri=%x\n", e_setup)
    A.f(lambda a, L: ldr_pc(R2, a, L["PG2"]))
    A.w(ldr(R2, R2, EOTF_OFF))
    gamma_from_eotf(A, R2, R0)
    A.w(str_(R2, SP, uslot))
    A.w(dp_imm(AL, MOV, 0, R2, GAMMA_EVENT))
    A.f(lambda a, L: branch(AL, a, back_e))
    # -- clear poll_pri but keep the gamma-pending bit
    A.label("C")
    trace(A, "C", "gm:C pri=%x\n", lambda adj: A.w(ldr(R1, R3, poll_off)))
    A.w(ldr(R2, R3, poll_off))
    A.w(dp_imm(AL, AND, R2, R2, 2))
    A.w(str_(R2, R3, poll_off))
    A.f(lambda a, L: branch(AL, a, exit_c))
    # -- stream changed: flag an eotf change
    A.label("S")
    def s_setup(adj):
        A.w(ldrb(R1, SP, new_eotf_sp - EOTF_OFF + adj))
        A.f(lambda a, L: ldr_pc(R2, a, L["PG2"]))
        A.w(ldr(R2, R2, EOTF_OFF))
        A.w(ldr(R3, SP, new_eotf_sp + adj))
    trace(A, "S", "gm:S valid=%d old=%d new=%d\n", s_setup)
    A.f(lambda a, L: ldr_pc(R0, a, L["PG2"]))
    A.w(ldrb(R3, SP, new_eotf_sp - EOTF_OFF))                    # stream info 'valid': every valid report is
    A.w(dp_imm(AL, CMP, R3, 0, 0, 1))                            # passed on, so a new HDR10 channel after an
    A.f(lambda a, L: branch(NE, a, L["FL"]))                     # HDR10 channel is reported as well
    A.w(ldr(R1, R0, EOTF_OFF))                                   # invalid (stopped): only an eotf change
    A.w(ldr(R2, SP, new_eotf_sp))
    A.w(dp_reg(AL, CMP, R1, 0, R2, 1))
    A.f(lambda a, L: branch(EQ, a, back_s))
    A.label("FL")
    A.f(lambda a, L: ldr_pc(R3, a, L["PG"]))
    A.w(ldr(R1, R3, poll_off))
    A.w(dp_imm(AL, ORR, R1, R1, 3))
    A.w(str_(R1, R3, poll_off))
    A.f(lambda a, L: ldr_pc(R0, a, L["PWQ"]))                    # wake the poll queue like bcm7335_dec_fifo_empty
    A.w(dp_imm(AL, MOV, 0, R1, 3))
    A.w(dp_imm(AL, MOV, 0, R2, 1))
    A.w(dp_imm(AL, MOV, 0, R3, 0))
    A.label("CALL2")
    A.w(0xebfffffe)                                              # bl __wake_up (relocated)
    A.f(lambda a, L: ldr_pc(R0, a, L["PG2"]))                    # r0 = stream info copy again for the memcpy
    A.f(lambda a, L: branch(AL, a, back_s))
    # -- /proc/stb/vmpeg/0/gamma   read(file r0, buf r1, count r2, ppos r3)
    A.label("R")
    A.w(PUSH_R4_R5_LR)
    A.w(dp_imm(AL, SUB, SP, SP, 8))
    A.w(0xe1a04001)                                              # mov r4,r1
    A.w(0xe1a05003)                                              # mov r5,r3
    A.w(ldr(R3, R5, 0))
    A.w(ldr(IP, R5, 4))
    A.w(0xe183300c)                                              # orr r3,r3,ip
    A.w(dp_imm(AL, CMP, R3, 0, 0, 1))
    A.f(lambda a, L: branch(NE, a, L["R0"]))                     # already read -> EOF
    A.w(dp_imm(AL, CMP, R2, 0, 2, 1))
    A.w(dp_imm(LO, MVN, 0, R0, 0x15))                            # -EINVAL
    A.f(lambda a, L: branch(LO, a, L["RET"]))
    A.f(lambda a, L: ldr_pc(R1, a, L["PG2"]))
    A.w(ldr(R1, R1, EOTF_OFF))
    gamma_from_eotf(A, R1, R3)
    A.w(dp_imm(AL, ADD, R1, R1, 0x30))
    A.w(dp_imm(AL, ORR, R1, R1, 0xa00))
    A.w(STRH_R1_SP)
    A.w(0xe1a00004)                                              # mov r0,r4
    A.w(0xe1a0100d)                                              # mov r1,sp
    A.w(dp_imm(AL, MOV, 0, R2, 2))
    A.label("CALL")
    A.w(0xebfffffe)                                              # bl __copy_to_user (relocated)
    A.w(dp_imm(AL, CMP, R0, 0, 0, 1))
    A.w(dp_imm(NE, MVN, 0, R0, 13))                              # -EFAULT
    A.f(lambda a, L: branch(NE, a, L["RET"]))
    A.w(dp_imm(AL, MOV, 0, R3, 2))
    A.w(str_(R3, R5, 0))
    A.w(dp_imm(AL, MOV, 0, R3, 0))
    A.w(str_(R3, R5, 4))
    A.w(dp_imm(AL, MOV, 0, R0, 2))
    A.f(lambda a, L: branch(AL, a, L["RET"]))
    A.label("R0")
    A.w(dp_imm(AL, MOV, 0, R0, 0))
    A.label("RET")
    A.w(dp_imm(AL, ADD, SP, SP, 8))
    A.w(POP_R4_R5_PC)
    for tag, text in strings:
        A.label("STR_" + tag)
        text += b"\0" * (-len(text) % 4)
        for k in range(0, len(text), 4):
            A.w(struct.unpack_from("<I", text, k)[0])
    A.label("PG")
    A.w(0)
    A.label("PG2")
    A.w(0)
    A.label("PWQ")
    A.w(0)

    # ---------------- cave
    runs = [r for r in dead_runs(e) if sum(x[1] for x in r) >= A.size() + 64]
    if not runs:
        raise Refuse("no unreferenced code to hold the new routines")
    run = max(runs, key=lambda r: sum(x[1] for x in r))
    cave = run[0][0]
    cave_end = cave + sum(x[1] for x in run)
    code, L = A.assemble(cave)
    used_end = cave + A.size()
    spare = [r for r in e.rels if used_end <= r[1] < cave_end] + [r for r in e.rels if cave <= r[1] < used_end]
    ptags = [t for t, _x in strings]
    if len(spare) < 5 + len(ptags):
        raise Refuse("not enough spare relocations in the cave")
    out = bytearray(data)

    def put(addr, word):
        struct.pack_into("<I", out, e.text_off + addr, word)

    def set_rel(ent, r_off, sym, typ):
        struct.pack_into("<II", out, ent[0], r_off, (sym << 8) | typ)

    chosen = spare[:5 + len(ptags)]
    for r in e.rels:
        if cave <= r[1] < used_end and r not in chosen:
            struct.pack_into("<I", out, r[0] + 4, (r[2] << 8) | R_ARM_NONE)
    set_rel(chosen[0], L["PG"], G[0], R_ARM_ABS32)
    set_rel(chosen[1], L["PG2"], G2[0], R_ARM_ABS32)
    set_rel(chosen[2], L["CALL"], copy_sym, R_ARM_CALL)
    set_rel(chosen[3], L["PWQ"], WQ[0], R_ARM_ABS32)
    set_rel(chosen[4], L["CALL2"], wake_sym, R_ARM_CALL)
    for k, tag in enumerate(ptags):
        set_rel(chosen[5 + k], L["CALLP_" + tag], e.undef["printk"], R_ARM_CALL)
    for i, w in enumerate(code):
        put(cave + 4 * i, w)
    put(L["PG"], G[1])
    put(L["PG2"], G2[1])
    put(L["PWQ"], WQ[1])
    put(hook_s, branch(AL, hook_s, L["S"]))
    put(hook_e, branch(AL, hook_e, L["E"]))
    put(hook_c, branch(AL, hook_c, L["C"]))
    put(hook_sc, branch(AL, hook_sc, L["SC"]))

    # proc entry: new name, new reader (text section symbol + offset), generic no-op writer
    dat = e.sec[e.byname[".data"]][4]
    struct.pack_into("<II", out, read_rel[0], read_rel[1], (e.text_secsym[0] << 8) | R_ARM_ABS32)
    struct.pack_into("<I", out, dat + read_rel[1], L["R"])
    struct.pack_into("<II", out, write_rel[0], write_rel[1], (wr_sym << 8) | R_ARM_ABS32)
    struct.pack_into("<I", out, dat + write_rel[1], 0)
    pos = ro[4] + name_off - 1
    out[pos:pos + len(NEW_NAME)] = NEW_NAME
    return bytes(out), dict(already=False, cave=cave, labels=L, hooks=(hook_s, hook_e, hook_c), debug=debug,
                            G=G, G2=G2, poll=poll_off)


def main():
    args = sys.argv[1:]
    check, outpath, debug = False, None, False
    while args and args[0].startswith("-"):
        if args[0] == "--check":
            check, args = True, args[1:]
        elif args[0] == "--debug":
            debug, args = True, args[1:]
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
        out, info = patch(data, debug)
    except Refuse as ex:
        print("patch_dvb_bcm7444_gamma: %s - NOT patching %s" % (ex, path))
        return 2
    if info["already"]:
        print("patch_dvb_bcm7444_gamma: %s is already patched, nothing to do" % path)
        return 0
    desc = "cave .text+%#x (stream hook %#x, event hook %#x, clear hook %#x)" % ((info["cave"],) + info["hooks"])
    if check:
        print("patch_dvb_bcm7444_gamma: %s can be patched, %s; not writing (--check)" % (path, desc))
        return 0
    assert len(out) == len(data)
    with open(outpath or path, "wb") as f:
        f.write(out)
    print("patch_dvb_bcm7444_gamma: patched %s -> %s, %s, sha256 %s"
          % (path, outpath or path, desc, hashlib.sha256(out).hexdigest()))
    return 0


if __name__ == "__main__":
    sys.exit(main())
