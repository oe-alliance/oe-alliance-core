SUMMARY  = "UDF reader"
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://code.videolan.org/videolan/libudfread.git;protocol=https"
SRCREV="131629921cc756c38eaf3e2d6b69ba2db690b199"

inherit gitpkgv autotools-brokensep pkgconfig

PV = "1.0.0+git${SRCPV}"
PKGV = "1.0.0+git${GITPKGV}"

S="${WORKDIR}/git"

do_package_qa() {
}
