DESCRIPTION = "ViXBMC HD skins for Enigma2"
MAINTAINER = "markus625"

require conf/license/license-gplv2.inc

inherit gitpkgv
EPSM = "enigma2-plugin-vix-vixbmc"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r3"

PACKAGES = "vix-vixbmc-hd-common ${EPSM}-slim-hd ${EPSM}-night-hd"
PROVIDES = "${PN} vix-vixbmc-hd-common ${EPSM}-slim-hd ${EPSM}-night-hd"

RCONFLICTS_vix-vixbmc-hd-common = "${EPSM}-hd-common"
RREPLACES_vix-vixbmc-hd-common = "${EPSM}-hd-common"

SRC_URI="git://github.com/OpenViX/vix-xbmc-skin.git;protocol=git"

FILES_vix-vixbmc-hd-common = "/usr/share/enigma2/ViXBMC_HD-Common"
FILES_${EPSM}-slim-hd = "/usr/share/enigma2/ViXBMC_HD-Slim"
FILES_${EPSM}-night-hd = "/usr/share/enigma2/ViXBMC_HD-Night"

RDEPENDS_${EPSM}-slim-hd = "vix-vixbmc-hd-common"
RDEPENDS_${EPSM}-night-hd = "vix-vixbmc-hd-common"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
