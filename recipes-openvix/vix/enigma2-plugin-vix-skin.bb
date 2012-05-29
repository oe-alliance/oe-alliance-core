DESCRIPTION = "ViX HD skins for Enigma2"
MAINTAINER = "Andy Blackburn"

require conf/license/openvix-gplv2.inc

inherit gitpkgv
EPSM = "enigma2-plugin-vix"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r6"

PACKAGES = "vix-hd-common ${EPSM}-day-hd ${EPSM}-night-hd"
PROVIDES = "${PN} vix-hd-common ${EPSM}-day-hd ${EPSM}-night-hd"

RCONFLICTS_vix-hd-common = "${EPSM}-hd-common"
RREPLACES_vix-hd-common = "${EPSM}-hd-common"
RCONFLICTS_${EPSM}-day-hd = "enigma2-plugin-vix-skins-day-hd"
RREPLACES_${EPSM}-day-hd = "enigma2-plugin-vix-skins-day-hd"
RCONFLICTS_${EPSM}-night-hd = "enigma2-plugin-vix-skins-night-hd"
RREPLACES_${EPSM}-night-hd = "enigma2-plugin-vix-skins-night-hd"

SRC_URI="git://git.assembla.com/openvix.8.git;protocol=git"

FILES_vix-hd-common = "/usr/share/enigma2/ViX_HD_Common"
FILES_${EPSM}-day-hd = "/usr/share/enigma2/ViX_Day_HD"
FILES_${EPSM}-night-hd = "/usr/share/enigma2/ViX_Night_HD"

RDEPENDS_${EPSM}-day-hd = "vix-hd-common"
RDEPENDS_${EPSM}-night-hd = "vix-hd-common"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
