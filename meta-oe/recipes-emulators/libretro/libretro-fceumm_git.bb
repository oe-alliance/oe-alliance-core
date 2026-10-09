SUMMARY = "FCEUmm libretro core"
DESCRIPTION = "Nintendo Entertainment System and Famicom emulator core."
HOMEPAGE = "https://github.com/libretro/libretro-fceumm"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Copying;md5=6e233eda45c807aa29aeaa6d94bc48a2"

SRC_URI = "git://github.com/libretro/libretro-fceumm.git;protocol=https;branch=master"
SRCREV = "7a542dab1e87679921962a9f056186eca425c0c2"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "fceumm_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
