SUMMARY = "PicoDrive libretro core"
DESCRIPTION = "Performance-oriented Sega Mega Drive, Mega-CD, 32X and Pico emulator core."
HOMEPAGE = "https://github.com/libretro/picodrive"

LICENSE = "LicenseRef-PicoDrive"
LIC_FILES_CHKSUM = "file://COPYING;md5=4613340462793d879916d43aa44d4236"

SRC_URI = "gitsm://github.com/libretro/picodrive.git;protocol=https;branch=master"
SRCREV = "1890c2932234c9d30f4cd3851d02228baae8f09e"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "picodrive_libretro.so"

# PicoDrive's ARM dynarec and hand-written assembly use code relocations that
# cannot be made position independent without disabling the fast ARM path.
# Keep the optimized build for receiver hardware and scope the exception to
# this core package only.
INSANE_SKIP:${PN} += "textrel"

do_compile() {
    oe_runmake -f Makefile.libretro platform=unix
}
