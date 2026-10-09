SUMMARY = "PrBoom libretro core"
DESCRIPTION = "Doom engine core for user-provided or freely licensed IWAD files."
HOMEPAGE = "https://github.com/libretro/libretro-prboom"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=14aa9744482b9e7ee47eef837e04c26e"

SRC_URI = "git://github.com/libretro/libretro-prboom.git;protocol=https;branch=master"
SRCREV = "c6e0fcb8325fc7969c91387f56c63433de0f1a53"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "prboom_libretro.so"

do_compile() {
    oe_runmake platform=linux-portable
}
