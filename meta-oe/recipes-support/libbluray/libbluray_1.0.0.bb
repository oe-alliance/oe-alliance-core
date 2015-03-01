SUMMARY  = "libbluray for oe."
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
DEPENDS = "libxml2"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"
PR = "r5"

S="${WORKDIR}/git"

SRCREV="491c7e8501d7e7ea853700a2f35a18f11ad40a5b"
SRC_URI = "git://git.videolan.org/libbluray.git;branch=master;protocol=git"

inherit autotools-brokensep pkgconfig

do_package_qa() {
}

FILES_${PN} = "/"
