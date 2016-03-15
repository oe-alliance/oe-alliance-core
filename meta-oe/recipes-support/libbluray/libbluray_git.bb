SUMMARY  = "Library to access Blu-Ray disk"
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"
PR = "r0"

DEPENDS = "libxml2"
RDEPENDS_${PN} = "libxml2"

SRC_URI = "git://git.videolan.org/libbluray.git;branch=master;protocol=git"
SRCREV="adefe3ba84c614eb62632b79107643db30ec6abf"

inherit gitpkgv
PV = "v0.9.2+git${SRCPV}"
PKGV = "v0.9.2+git${GITPKGV}"

S="${WORKDIR}/git"

EXTRA_OECONF = " \
    --disable-bdjava \
    --disable-bdjava-jar \
    --disable-doxygen-doc \
    --disable-doxygen-dot \
    --disable-udf \
    --without-fontconfig \
"

inherit autotools-brokensep pkgconfig

do_package_qa() {
}

FILES_${PN} = "/"
