DESCRIPTION = "enigma2-plugin-extensions-weatherplugin"
MAINTAINER = "opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
VER ="3.0"
PR = "r1"

SRC_URI="git://github.com/opendroid-Team/enigma2-plugin-extensions-weatherplugin.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}"


do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}
