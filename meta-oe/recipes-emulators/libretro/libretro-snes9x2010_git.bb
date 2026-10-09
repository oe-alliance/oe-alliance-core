SUMMARY = "Snes9x 2010 libretro core"
DESCRIPTION = "Performance-oriented Super Nintendo and Super Famicom emulator core."
HOMEPAGE = "https://github.com/libretro/snes9x2010"

LICENSE = "LicenseRef-Snes9x"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=82f2245ecff2ebb94c72bbce5002e25c"

SRC_URI = "git://github.com/libretro/snes9x2010.git;protocol=https;branch=master"
SRCREV = "fe690dd321fa5a46b5234a2bde089d2518c62b0e"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "snes9x2010_libretro.so"

do_compile() {
    oe_runmake -f Makefile.libretro platform=unix
}
