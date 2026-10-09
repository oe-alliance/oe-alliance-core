SUMMARY = "PUAE libretro core"
DESCRIPTION = "Commodore Amiga emulator core based on UAE."
HOMEPAGE = "https://github.com/libretro/libretro-uae"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

SRC_URI = "git://github.com/libretro/libretro-uae.git;protocol=https;branch=master \
           file://0001-libmpeg2-preserve-file-offset-bits.patch \
"
SRCREV = "7387937066b1b693816c3b4e2b7a0c601d41363d"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "puae_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
