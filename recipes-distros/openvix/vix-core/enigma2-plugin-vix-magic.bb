DESCRIPTION = "Magic skins for Enigma2"
MAINTAINER = "Rob van der Does"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins-vix"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r11"

RCONFLICTS_${EPSM}-hd = "enigma2-plugin-vix-magic-hd"
RREPLACES_${EPSM}-hd = "enigma2-plugin-vix-magic-hd"
RCONFLICTS_${EPSM}-hd-lite = "enigma2-plugin-vix-magic-hd-lite"
RREPLACES_${EPSM}-hd-lite = "enigma2-plugin-vix-magic-hd-lite"
RCONFLICTS_${EPSM}-hd-night = "enigma2-plugin-vix-magic-hd-night"
RREPLACES_${EPSM}-hd-night = "enigma2-plugin-vix-magic-hd-night"

PACKAGES = "vix-magic-hd-common ${EPSM}-hd ${EPSM}-hd-lite ${EPSM}-hd-night"
PROVIDES = "${PN} vix-magic-hd-common ${EPSM}-hd ${EPSM}-hd-lite ${EPSM}-hd-night"

SRC_URI="git://github.com/OpenViX/vix-magic-skins.git;protocol=git"

FILES_vix-magic-hd-common = "/usr/share/enigma2/Magic-HD-Common/"
FILES_${EPSM}-hd = "/usr/share/enigma2/Magic-HD/"
FILES_${EPSM}-hd-lite = "/usr/share/enigma2/Magic-HD-Light/"
FILES_${EPSM}-hd-night = "/usr/share/enigma2/Magic-HD-Night/"

RDEPHD = "font-valis-hd vix-magic-hd-common"
RDEPENDS_${EPSM}-hd = "${RDEPHD}"
RDEPENDS_${EPSM}-hd-lite = "${RDEPHD}"
RDEPENDS_${EPSM}-hd-night = "${RDEPHD}"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
