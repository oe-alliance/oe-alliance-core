SUMMARY = "Stella 2014 libretro core"
DESCRIPTION = "Atari 2600 emulator core derived from Stella."
HOMEPAGE = "https://github.com/libretro/stella2014-libretro"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://stella/license.txt;md5=435d4178fd08b25f9cf911f1c3a0ce1d"

SRC_URI = "git://github.com/libretro/stella2014-libretro.git;protocol=https;branch=master"
SRCREV = "7d1361e407e63f29e52892655069e5fb4096e691"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "stella2014_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
