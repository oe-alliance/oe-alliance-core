SUMMARY = "Enigma2 Skin oDreamy"
MAINTAINER = "openDroid Team"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"
VER="1.1"
PR = "r2"

SRC_URI="git://github.com/opendroid-Team/skins-oDreamy.git"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir} /usr/share"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
}
