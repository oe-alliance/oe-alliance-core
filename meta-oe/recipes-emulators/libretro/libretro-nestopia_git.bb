SUMMARY = "Nestopia UE libretro core"
DESCRIPTION = "Nintendo Entertainment System and Famicom emulator core."
HOMEPAGE = "https://github.com/libretro/nestopia"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=686e6cb566fd6382c9fcc7a557bf4544"

SRC_URI = "git://github.com/libretro/nestopia.git;protocol=https;branch=master"
SRCREV = "b9fdc9c4e6d374abacd1a678ae46ec7f963ef59a"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "nestopia_libretro.so"
LIBRETRO_CORE_PATH = "${S}/libretro/${LIBRETRO_CORE_FILE}"

do_compile() {
    oe_runmake -C ${S}/libretro platform=unix
}
