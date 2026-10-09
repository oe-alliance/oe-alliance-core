SUMMARY = "FinalBurn Neo libretro core"
DESCRIPTION = "Arcade and console emulator core based on FinalBurn Neo."
HOMEPAGE = "https://github.com/libretro/FBNeo"

LICENSE = "LicenseRef-FBNeo"
LIC_FILES_CHKSUM = "file://src/license.txt;md5=f61de60ada8817f1957a71b82dd3091f"

SRC_URI = "git://github.com/libretro/FBNeo.git;protocol=https;branch=master"
SRCREV = "a49cfac4b97cc62d0196c1d0cde8f5b14fde662c"
PV = "0.1+git20261008.${SRCPV}"

require libretro-core.inc

LIBRETRO_CORE_FILE = "fbneo_libretro.so"
LIBRETRO_CORE_PATH = "${S}/src/burner/libretro/${LIBRETRO_CORE_FILE}"

CLEANBROKEN = "1"

do_compile() {
    oe_runmake -C ${S}/src/burner/libretro platform=unix
}
