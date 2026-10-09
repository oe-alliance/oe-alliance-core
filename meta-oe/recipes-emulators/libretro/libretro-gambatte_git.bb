SUMMARY = "Gambatte libretro core"
DESCRIPTION = "Nintendo Game Boy and Game Boy Color emulator core."
HOMEPAGE = "https://github.com/libretro/gambatte-libretro"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "git://github.com/libretro/gambatte-libretro.git;protocol=https;branch=master"
SRCREV = "d9d6cd06382d1ced30de34d56d3609452323dab1"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "gambatte_libretro.so"

do_compile() {
    oe_runmake platform=unix
}
