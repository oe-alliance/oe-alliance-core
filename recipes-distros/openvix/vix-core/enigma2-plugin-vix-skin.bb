DESCRIPTION = "ViX HD skins for Enigma2"
MAINTAINER = "Andy Blackburn"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins-vix"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r10"

PACKAGES = "vix-skin-common ${EPSM}-day-hd ${EPSM}-night-hd"
PROVIDES = "${PN} vix-skin-common ${EPSM}-day-hd ${EPSM}-night-hd"

RCONFLICTS_vix-skin-common = "vix-hd-common"
RREPLACES_vix-skin-common = "vix-hd-common"
RCONFLICTS_${EPSM}-day-hd = "enigma2-plugin-vix-day-hd"
RREPLACES_${EPSM}-day-hd = "enigma2-plugin-vix-day-hd"
RCONFLICTS_${EPSM}-night-hd = "enigma2-plugin-vix-night-hd"
RREPLACES_${EPSM}-night-hd = "enigma2-plugin-vix-night-hd"

SRC_URI="git://github.com/OpenViX/vix-skins.git;protocol=git"

FILES_vix-skin-common = "/usr/share/enigma2/ViX-Common"
FILES_${EPSM}-day-hd = "/usr/share/enigma2/ViX-Day-HD"
FILES_${EPSM}-night-hd = "/usr/share/enigma2/ViX-Night-HD"

RDEPENDS_${EPSM}-day-hd = "vix-skin-common"
RDEPENDS_${EPSM}-night-hd = "vix-skin-common"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
