SUMMARY = "Free 2048 game for the libretro API"
DESCRIPTION = "A contentless, public-domain test core used to validate video, audio and controller input without shipping copyrighted ROMs."
HOMEPAGE = "https://github.com/libretro/libretro-2048"

LICENSE = "Unlicense"
LIC_FILES_CHKSUM = "file://COPYING;md5=61287f92700ec1bdf13bc86d8228cd13"

SRC_URI = "git://github.com/libretro/libretro-2048.git;protocol=https;branch=master"
SRCREV = "39333f7b13dc4daea7c151d9c38d22b961246343"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "2048_libretro.so"

do_compile() {
    oe_runmake -f Makefile.libretro \
        platform=unix \
        CC="${CC}" \
        CXX="${CXX}"
}
