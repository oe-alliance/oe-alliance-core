SUMMARY = "PCSX ReARMed libretro core"
DESCRIPTION = "ARM-optimized Sony PlayStation emulator core with dynamic recompilation."
HOMEPAGE = "https://github.com/libretro/pcsx_rearmed"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=5dd99a4a14d516c44d0779c1e819f963"

SRC_URI = "gitsm://github.com/libretro/pcsx_rearmed.git;protocol=https;branch=master"
SRCREV = "c8816799b50388e61cfe237fe2cdbb7d8175f20a"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "pcsx_rearmed_libretro.so"

do_compile() {
    oe_runmake -f Makefile.libretro platform=unix ARCH_DETECTED="${TARGET_ARCH}"
}
