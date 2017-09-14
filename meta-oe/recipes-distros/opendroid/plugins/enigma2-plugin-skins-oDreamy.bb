SUMMARY = "Enigma2 Skin openDroid"
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
VER ="1.1"
PR = "r1"

SRC_URI="git://github.com/opendroid-Team/skins-oDreamy.git;protocol=git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}
