DESCRIPTION = "Library to access Blu-Ray disk"
SECTION = "libs"
HOMEPAGE = "http://videolan.org"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM="file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"

DEPENDS = "freetype libaacs libdca libdvdcss libxml2"
RDEPENDS:${PN} = "libaacs libdca libdvdcss"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "v1.3.4+git${SRCPV}"
PKGV = "v1.3.4+git${GITPKGV}"

SRC_URI = "gitsm://code.videolan.org/videolan/libbluray.git;protocol=https;branch=master"

S="${WORKDIR}/git"

EXTRA_OECONF = " \
    --disable-bdjava-jar \
    --disable-doxygen-doc \
    --disable-doxygen-dot \
    --without-freetype \
    --without-fontconfig \
"

inherit autotools-brokensep pkgconfig

FILES:${PN} = "/"

do_package_qa() {
}
