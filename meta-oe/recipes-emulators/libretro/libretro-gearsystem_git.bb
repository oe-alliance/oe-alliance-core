SUMMARY = "Gearsystem libretro core"
DESCRIPTION = "Sega Master System, Game Gear and SG-1000 emulator core."
HOMEPAGE = "https://github.com/drhelius/Gearsystem"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/drhelius/Gearsystem.git;protocol=https;branch=master"
SRCREV = "54a1eeb00186cf3aa2b82cee73eefc7b3377f9f5"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "gearsystem_libretro.so"
LIBRETRO_CORE_PATH = "${S}/platforms/libretro/${LIBRETRO_CORE_FILE}"

do_compile() {
    oe_runmake -C ${S}/platforms/libretro platform=unix
}
