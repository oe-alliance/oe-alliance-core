SUMMARY = "Open implementation of the AACS specification"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"
DEPENDS = "libgcrypt"
SRCREV = "668bd765cd3bdaf8216ad0fa26e01286029c0499"
PV = "0.4.0"

SRC_URI = "git://git.videolan.org/${PN}.git;protocol=git"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig
