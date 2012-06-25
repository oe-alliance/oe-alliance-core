DESCRIPTION = "Magic skins for Enigma2"
MAINTAINER = "Rob van der Does"

require conf/license/openvix-gplv2.inc

inherit gitpkgv

EPSM = "enigma2-plugin-vix-magic"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r7"

RCONFLICTS_${EPSM}-sd = "enigma2-plugin-skins-magic"
RREPLACES_${EPSM}-sd = "enigma2-plugin-skins-magic"

PV_font-valis-enigma = "2009.11.12"
PR_font-valis-enigma = "r1"
PKGV_font-valis-enigma = "${PV_font-valis-enigma}"
DESCRIPTION_font-valis-enigma = "Valis enigma font"

PACKAGES = "font-valis-enigma vix-magic-hd-common ${EPSM}-sd ${EPSM}-hd ${EPSM}-ehd ${EPSM}-hd-lite ${EPSM}-hd-night ${EPSM}-ehd-lite"
PROVIDES = "${PN} font-valis-enigma vix-magic-hd-common ${EPSM}-sd ${EPSM}-hd ${EPSM}-ehd ${EPSM}-hd-lite ${EPSM}-hd-night ${EPSM}-ehd-lite"

SRC_URI="git://git.assembla.com/openvix.7.git;protocol=git"

FILES_vix-magic-hd-common = "/usr/share/enigma2/Magic-HD-Common/"
FILES_font-valis-enigma = "/usr/share/fonts/valis_enigma.ttf"
FILES_${EPSM}-sd = "/usr/share/enigma2/Magic/"
FILES_${EPSM}-hd = "/usr/share/enigma2/Magic-HD/"
FILES_${EPSM}-hd-lite = "/usr/share/enigma2/Magic-HD-Light/"
FILES_${EPSM}-hd-night = "/usr/share/enigma2/Magic-HD-Night/"

RDEPSD = "font-valis-enigma"
RDEPHD = "font-valis-hd vix-magic-hd-common"
RDEPENDS_${EPSM}-sd = "${RDEPSD}"
RDEPENDS_${EPSM}-hd = "${RDEPHD}"
RDEPENDS_${EPSM}-hd-lite = "${RDEPHD}"
RDEPENDS_${EPSM}-hd-night = "${RDEPHD}"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
	chmod 644 ${D}/usr/share/fonts/*.ttf
}
