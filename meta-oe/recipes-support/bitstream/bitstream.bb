SUMMARY = "biTStream is a set of C headers"
DESCRIPTION = "biTStream is a set of C headers allowing a simpler access to binary \
	structures such as specified by MPEG, DVB, IETF, SMPTE, IEEE, SCTE, etc."
SECTION = "multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=7decd8ef15ab16ed5436851272b61cf7"

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRCREV = "e9408996e7557e4a6b9f8e2e7ea1b1cb959bf837"
SRC_URI = "git://code.videolan.org/videolan/bitstream.git;protocol=http \
    file://makefile_clean.patch \
"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

do_compile_prepend() {
	sed -i 's#/usr/local#/usr#' ${S}/Makefile
}

do_install_append() {
	install -d ${D}${includedir}
}

