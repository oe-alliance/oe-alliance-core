SUMMARY  = "UDF reader"
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://git.videolan.org/libudfread.git;branch=master;protocol=git"
SRCREV="50d41b171c90d299ee3f685bbe6298d15a44eec0"

inherit gitpkgv autotools-brokensep pkgconfig

PV = "0.0.0+git${SRCPV}"
PKGV = "0.0.0+git${GITPKGV}"

S="${WORKDIR}/git"

do_package_qa() {
}
