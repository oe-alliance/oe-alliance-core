SUMMARY = "MAME 2003-Plus libretro core"
DESCRIPTION = "Performance-oriented arcade emulator core based on MAME 0.78."
HOMEPAGE = "https://github.com/libretro/mame2003-plus-libretro"

LICENSE = "LicenseRef-MAME-2003-Plus"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=60e1e140c3210d8a8741631c9e77f091"

SRC_URI = "git://github.com/libretro/mame2003-plus-libretro.git;protocol=https;branch=master"
SRCREV = "10315f744e8dcd14136ef873f91c490e5499dc5c"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "mame2003_plus_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
