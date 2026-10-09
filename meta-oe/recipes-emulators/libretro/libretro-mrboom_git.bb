SUMMARY = "Mr.Boom libretro core"
DESCRIPTION = "Free multiplayer Bomberman-style game for libretro."
HOMEPAGE = "https://github.com/libretro/mrboom-libretro"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e7d8cb796ca7b5ac0cdb18c3e2749d97"

SRC_URI = "gitsm://github.com/libretro/mrboom-libretro.git;protocol=https;branch=master"
SRCREV = "40ac32020b540c4bb418c7d798a990932a65da04"
PV = "0.1+git${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "mrboom_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
