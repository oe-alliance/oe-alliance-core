DESCRIPTION = "Magic skins for Enigma2"
MAINTAINER = "Rob van der Does"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-vix-magic"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r10"

RCONFLICTS_${EPSM}-sd = "enigma2-plugin-skins-magic"
RREPLACES_${EPSM}-sd = "enigma2-plugin-skins-magic"

PACKAGES = "vix-magic-hd-common ${EPSM}-hd ${EPSM}-ehd ${EPSM}-hd-lite ${EPSM}-hd-night ${EPSM}-ehd-lite"
PROVIDES = "${PN} vix-magic-hd-common ${EPSM}-hd ${EPSM}-ehd ${EPSM}-hd-lite ${EPSM}-hd-night ${EPSM}-ehd-lite"

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
