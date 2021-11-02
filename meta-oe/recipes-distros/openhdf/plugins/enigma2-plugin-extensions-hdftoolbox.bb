MODULE = "HDF-Toolbox"
SUMMARY = "HDFreaks.cc Toolbox"
MAINTAINER = "HDFreaks"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

DEPENDS = "enigma2"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r3"

SRC_URI="git://github.com/openhdf/hdftoolbox.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}
