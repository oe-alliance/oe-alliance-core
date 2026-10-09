SUMMARY = "ClownMDEmu libretro core"
DESCRIPTION = "Portable Sega Mega Drive, Genesis and Mega-CD emulator core."
HOMEPAGE = "https://github.com/Clownacy/clownmdemu-libretro"

LICENSE = "AGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENCE.txt;md5=4ae09d45eac4aa08d013b5f2e01c67f6"

SRC_URI = "gitsm://github.com/Clownacy/clownmdemu-libretro.git;protocol=https;branch=master"
SRCREV = "d43c2708b0a31c285ce16724b6c4a2e92af07346"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "clownmdemu_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
