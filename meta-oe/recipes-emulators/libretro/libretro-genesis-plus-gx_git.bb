SUMMARY = "Genesis Plus GX libretro core"
DESCRIPTION = "Sega 8-bit and 16-bit console emulator core."
HOMEPAGE = "https://github.com/libretro/Genesis-Plus-GX"

LICENSE = "LicenseRef-Genesis-Plus-GX"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d4817c708af18ab478a033d92db6b7a9"

SRC_URI = "git://github.com/libretro/Genesis-Plus-GX.git;protocol=https;branch=master"
SRCREV = "58c341487e5bfcf979ea68413c7987633adb0c56"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "genesis_plus_gx_libretro.so"

do_compile() {
    oe_runmake -f Makefile.libretro platform=unix
}
