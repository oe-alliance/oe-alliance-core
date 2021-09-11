SUMMARY = "biTStream is a set of C headers"
DESCRIPTION = "biTStream is a set of C headers allowing a simpler access to binary \
            structures such as specified by MPEG, DVB, IETF, SMPTE, IEEE, SCTE, etc."
SECTION = "multimedia"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=7decd8ef15ab16ed5436851272b61cf7"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.4+git${SRCPV}"
PKGV = "1.4+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/bitstream.git;protocol=https"

S = "${WORKDIR}/git"

CFLAGS_append_sh4 += "-std=gnu99"

inherit autotools-brokensep pkgconfig

do_compile_prepend() {
    sed -i 's#/usr/local#/usr#' ${S}/Makefile
}

do_install_append() {
    install -d ${D}${includedir}
}
