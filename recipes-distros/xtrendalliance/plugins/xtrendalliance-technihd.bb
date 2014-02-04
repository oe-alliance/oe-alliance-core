DESCRIPTION = "Enigma2 Skin TechniHD"
MAINTAINER = "Xtrend-Alliance"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

PROVIDES += "enigma2-plugin-skins-technihd"
RPROVIDES_${PN} += "enigma2-plugin-skins-technihd"

RREPLACES_${PN} += "enigma2-plugin-skins-tehnihd enigma2-plugin-skins-technihd-v2.0"
RCONFLICTS_${PN} += "enigma2-plugin-skins-tehnihd enigma2-plugin-skins-technihd-v2.0"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r5"

SRC_URI="git://github.com/xtrend-alliance/TechniHD.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}
