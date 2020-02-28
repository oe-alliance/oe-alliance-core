DESCRIPTION = "Library to access Blu-Ray disk"
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"

DEPENDS = "freetype libaacs libdca libdvdcss libxml2"
RDEPENDS_${PN} = "libaacs libdca libdvdcss"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "v1.1.2+git${SRCPV}"
PKGV = "v1.1.2+git${GITPKGV}"

SRC_URI = "gitsm://code.videolan.org/videolan/libbluray.git;protocol=https"

S="${WORKDIR}/git"

EXTRA_OECONF = " \
    --disable-bdjava-jar \
    --disable-doxygen-doc \
    --disable-doxygen-dot \
    --without-freetype \
    --without-fontconfig \
"

inherit autotools-brokensep pkgconfig

FILES_${PN} = "/"

do_package_qa() {
}
