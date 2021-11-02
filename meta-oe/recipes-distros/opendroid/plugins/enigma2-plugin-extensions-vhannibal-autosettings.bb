DESCRIPTION = "vhannibal autosettings plugin"
MAINTAINER = "opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.4+git${SRCPV}"
PKGV = "1.4+git${GITPKGV}"
VER ="1.4"
PR = "r4"

SRC_URI="git://github.com/opendroid-Team/vhannibal-autosettings.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}
