SUMMARY  = "UDF reader"
SECTION = "libs"
HOMEPAGE = "http://videolan.org"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM="file://COPYING;md5=4fbd65380cdd255951079008b364516c"

inherit gitpkgv

SRCREV="${AUTOREV}"
PV = "1.0.0+git"
PKGV = "1.0.0+git${GITPKGV}"

SRC_URI = "git://code.videolan.org/videolan/libudfread;protocol=https;branch=master"

inherit autotools-brokensep pkgconfig

S="${WORKDIR}/git"
