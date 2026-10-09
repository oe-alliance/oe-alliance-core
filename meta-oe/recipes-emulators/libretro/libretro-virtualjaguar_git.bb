SUMMARY = "Virtual Jaguar libretro core"
DESCRIPTION = "Atari Jaguar and Jaguar CD emulator core for high-performance AArch64 receivers."
HOMEPAGE = "https://github.com/libretro/virtualjaguar-libretro"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/libretro/virtualjaguar-libretro.git;protocol=https;branch=master"
SRCREV = "dbed6843a22e3fdbbbfada23d4663e8bdee4bc16"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "virtualjaguar_libretro.so"

do_compile() {
    oe_runmake platform=unix
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "${@bb.utils.contains('MACHINE_FEATURES', 'retrogaming-highperformance', '.*', '^$', d)}"
