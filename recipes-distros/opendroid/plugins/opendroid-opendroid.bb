DESCRIPTION = "Enigma2 Skin mega32"
MAINTAINER = "Opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

PROVIDES += "enigma2-plugin-skins-opendroid"
RPROVIDES_${PN} += "enigma2-plugin-skins-opendroid"

RREPLACES_${PN} += "enigma2-plugin-skins-opendroid enigma2-plugin-skins-opendroid"
RCONFLICTS_${PN} += "enigma2-plugin-skins-opendroid enigma2-plugin-skins-opendroid"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r6"

SRC_URI="git://github.com/opendroid-Team/mega32.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}
