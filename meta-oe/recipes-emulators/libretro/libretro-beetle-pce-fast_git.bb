SUMMARY = "Beetle PCE Fast libretro core"
DESCRIPTION = "NEC PC Engine, TurboGrafx-16 and SuperGrafx emulator core."
HOMEPAGE = "https://github.com/libretro/beetle-pce-fast-libretro"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6e233eda45c807aa29aeaa6d94bc48a2"

SRC_URI = "git://github.com/libretro/beetle-pce-fast-libretro.git;protocol=https;branch=master"
SRCREV = "3f946f277aef3aa99a95551618bbcd1dd2bda0d9"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "mednafen_pce_fast_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
