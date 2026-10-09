SUMMARY = "Beetle Supafaust libretro core"
DESCRIPTION = "ARM-oriented Super Nintendo and Super Famicom emulator core."
HOMEPAGE = "https://github.com/libretro/supafaust"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/libretro/supafaust.git;protocol=https;branch=master"
SRCREV = "642d1d1b6684aa7e306a02a89885f3f5456a5157"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "mednafen_supafaust_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
