SUMMARY = "create DVD-Video file system"
SECTION = "console/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
DEPENDS = "freetype libdvdread libfribidi libpng libxml2 zlib"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.7.2+git${SRCPV}"
PKGV = "0.7.2+git${GITPKGV}"
VER ="0.7.2"
PR = "r1"

SRC_URI="git://github.com/atvcaptain/dvdauthor.git"

S = "${WORKDIR}/git"

inherit autotools gettext pkgconfig

EXTRA_OECONF = " \
        ac_cv_prog_MAGICKCONFIG= \
        ac_cv_prog_GMAGICKCONFIG= \
"
