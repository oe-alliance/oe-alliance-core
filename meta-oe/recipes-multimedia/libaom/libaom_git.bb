SUMMARY = "AV1 Video Codec Library"
LICENSE = "BSD-2-Clause"
HOMEPAGE = "https://aomedia.org/"
SECTION = "libs/multimedia"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6ea91368c1bbdf877159435572b931f5"

DEPENDS = "nasm-native"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0.0+git"
PKGV = "1.0.0+git${GITPKGV}"

SRC_URI = "git://aomedia.googlesource.com/aom;branch=master;protocol=https"

S = "${WORKDIR}/git"

inherit cmake

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += "-DCMAKE_EXE_LINKER_FLAGS+=-pie -DCMAKE_POSITION_INDEPENDENT_CODE=1"
