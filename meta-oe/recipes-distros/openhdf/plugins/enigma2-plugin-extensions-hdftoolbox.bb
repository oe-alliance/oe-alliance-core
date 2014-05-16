MODULE = "HDF-Toolbox"
SUMMARY = "HDFreaks.cc Toolbox"
MAINTAINER = "HDFreaks"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

DEPENDS = "enigma2"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r1"

SRC_URI="git://github.com/openhdf/hdftoolbox.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}
