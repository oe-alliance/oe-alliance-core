SUMMARY = "DVD access multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=64e753fa7d1ca31632bc383da3b57c27"
inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "4.2.+git${SRCPV}"
PKGV = "4.2.+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/microe/libdvdread.git"
S = "${WORKDIR}/git"

inherit autotools lib_package binconfig pkgconfig
