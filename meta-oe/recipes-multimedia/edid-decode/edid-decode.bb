SUMMARY = "EDID decoder and conformance tester"
DESCRIPTION = "edid-decode decodes EDID monitor description data in human-readable format"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://edid-decode.cpp;beginline=1;endline=8;md5=bff0dd3acca4abcb1cdce2d39db63524"

RDEPENDS_${PN}_remove = "libm6"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://git.linuxtv.org/cgit.cgi/edid-decode.git;protocol=https"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig
