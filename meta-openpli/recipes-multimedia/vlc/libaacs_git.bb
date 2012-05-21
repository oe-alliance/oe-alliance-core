SUMMARY = "Open implementation of the AACS specification"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"
DEPENDS = "libgcrypt"
SRCREV = "7801a713e7360f84d771b6027de69c051f2499c2"
PV = "0.3.0+git${SRCPV}"
PR = "r1"

SRC_URI = "git://git.videolan.org/${PN}.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig
