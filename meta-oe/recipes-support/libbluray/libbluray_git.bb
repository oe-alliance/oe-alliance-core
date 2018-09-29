SUMMARY  = "Library to access Blu-Ray disk"
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"

DEPENDS = "libxml2"

SRC_URI = "gitsm://git.videolan.org/libbluray.git"

inherit gitpkgv autotools-brokensep pkgconfig

SRCREV = "${AUTOREV}"
PV = "v1.0.2+git${SRCPV}"
PKGV = "v1.0.2+git${GITPKGV}"

S="${WORKDIR}/git"

EXTRA_OECONF = " \
    --disable-bdjava-jar \
    --disable-doxygen-doc \
    --disable-doxygen-dot \
    --without-freetype \
    --without-fontconfig \
"

do_package_qa() {
}
