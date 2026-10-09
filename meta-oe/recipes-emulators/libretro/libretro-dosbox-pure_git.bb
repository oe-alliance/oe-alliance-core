SUMMARY = "DOSBox Pure libretro core"
DESCRIPTION = "DOS emulator core with archive, disk image and gamepad-oriented support."
HOMEPAGE = "https://codeberg.org/schelling/dosbox-pure"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7c050190136f70e95bb9873bf63cf427"

SRC_URI = "git://codeberg.org/schelling/dosbox-pure.git;protocol=https;branch=main \
           file://0001-dosbox-pure-enable-format-warnings.patch \
           file://0002-dosbox-pure-slow-tv-gamepad-osk-cursor.patch \
"
SRCREV = "9911d3b60838cabf4cfe727ccdfc669770c7186d"
PV = "0.1+git20261008.${SRCPV}"
PR = "r1"

require libretro-core.inc

LIBRETRO_CORE_FILE = "dosbox_pure_libretro.so"

do_compile() {
    oe_runmake platform=unix STRIPCMD=:
}
