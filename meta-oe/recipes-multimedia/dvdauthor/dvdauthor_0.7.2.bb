SUMMARY = "create DVD-Video file system"
SECTION = "console/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "freetype libdvdread fribidi libpng fontconfig libxml2 zlib bison-native"

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.7.2+git${SRCPV}"
PKGV = "0.7.2+git${GITPKGV}"

SRC_URI = "git://github.com/ldo/dvdauthor.git;protocol=https \
        file://dont-build-docs.patch"

S = "${WORKDIR}/git"

inherit autotools-brokensep gettext pkgconfig

EXTRA_OECONF = " \
        ac_cv_prog_MAGICKCONFIG= \
        ac_cv_prog_GMAGICKCONFIG= \
"

do_configure_prepend() {
# fix config.rpath file not found, create if it does not exist (in case of rebuilding without rm_work
if [ ! -f autotools/config.rpath ]; then
    mkdir -p autotools
    touch autotools/config.rpath
fi
}
