SUMMARY = "VICE x64 libretro core"
DESCRIPTION = "Commodore 64 emulator core from the VICE emulator suite."
HOMEPAGE = "https://github.com/libretro/vice-libretro"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=c93c0550bd3173f4504b2cbd8991e50b"

SRC_URI = "git://github.com/libretro/vice-libretro.git;protocol=https;branch=master"
SRCREV = "f63b56688f3133a2bb17499db9eebb7ab81df5f5"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "vice_x64_libretro.so"

do_compile() {
    oe_runmake platform=unix EMUTYPE=x64
}
