SUMMARY = "Open implementation of the AACS specification"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"

DEPENDS = "libgcrypt"

PV = "0.11.0+git${SRCPV}"
PKGV = "0.11.0+git${GITPKGV}"

SRCREV = "${AUTOREV}"
SRC_URI = "git://code.videolan.org/videolan/${BPN}.git;protocol=https"

S = "${WORKDIR}/git"

inherit gitpkgv autotools lib_package pkgconfig
