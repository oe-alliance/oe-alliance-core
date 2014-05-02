SUMMARY = "ViX HD skins for Enigma2"
MAINTAINER = "Andy Blackburn"

require conf/license/license-gplv2.inc

ALLOW_EMPTY_${PN} = "1"

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins-vix"
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r21"

PACKAGES = "${PN}-doc vix-skin-common ${EPSM}-day-hd ${EPSM}-night-hd ${EPSM}-magic-sd ${EPSM}-magic-hd ${EPSM}-magic-hd-light vix-mhdc ${EPSM}-magic-hd-noire ${EPSM}-magic-hd-night vix-vixbmc-hd-common ${EPSM}-vixbmc-slim-hd ${EPSM}-vixbmc-night-hd ${EPSM}-vixbmc-metropolis"
PROVIDES = "${PN} vix-skin-common ${EPSM}-day-hd ${EPSM}-night-hd ${EPSM}-magic-sd ${EPSM}-magic-hd ${EPSM}-magic-hd-light vix-mhdc ${EPSM}-magic-hd-noire ${EPSM}-magic-hd-night vix-vixbmc-hd-common ${EPSM}-vixbmc-slim-hd ${EPSM}-vixbmc-night-hd ${EPSM}-vixbmc-metropolis"

RCONFLICTS_vix-skin-common = "vix-hd-common"
RREPLACES_vix-skin-common = "vix-hd-common"
RCONFLICTS_${EPSM}-day-hd = "enigma2-plugin-vix-day-hd"
RREPLACES_${EPSM}-day-hd = "enigma2-plugin-vix-day-hd"
RCONFLICTS_${EPSM}-night-hd = "enigma2-plugin-vix-night-hd"
RREPLACES_${EPSM}-night-hd = "enigma2-plugin-vix-night-hd"
RCONFLICTS_${EPSM}-magic-hd = "enigma2-plugin-vix-magic-hd"
RREPLACES_${EPSM}-magic-hd = "enigma2-plugin-vix-magic-hd"
RCONFLICTS_${EPSM}-magic-hd-night = "enigma2-plugin-vix-magic-hd-night"
RREPLACES_${EPSM}-magic-hd-night = "enigma2-plugin-vix-magic-hd-night"
RCONFLICTS_${EPSM}-vixbmc-slim-hd = "enigma2-plugin-vix-vixbmc-slim-hd"
RREPLACES_${EPSM}-vixbmc-slim-hd = "enigma2-plugin-vix-vixbmc-slim-hd"
RCONFLICTS_${EPSM}-vixbmc-night-hd = "enigma2-plugin-vix-vixbmc-night-hd"
RREPLACES_${EPSM}-vixbmc-night-hd = "enigma2-plugin-vix-vixbmc-night-hd"
RCONFLICTS_${EPSM}-vixbmc-metropolis = "enigma2-plugin-vix-vixbmc-metropolis"
RREPLACES_${EPSM}-vixbmc-metropolis = "enigma2-plugin-vix-vixbmc-metropolis"

SRC_URI="git://github.com/OpenViX/vix-skins.git;protocol=git"

FILES_${PN}-doc = "/usr/share/enigma2/README*"
FILES_vix-skin-common = "/usr/share/enigma2/ViX-Common"
FILES_${EPSM}-day-hd = "/usr/share/enigma2/ViX-Day-HD"
FILES_${EPSM}-night-hd = "/usr/share/enigma2/ViX-Night-HD"

FILES_${EPSM}-magic-sd = "/usr/share/enigma2/Magic-SD/"
FILES_${EPSM}-magic-hd = "/usr/share/enigma2/Magic-HD/"
FILES_${EPSM}-magic-hd-light = "/usr/share/enigma2/Magic-HD-Light/"

FILES_vix-mhdc = "/usr/share/enigma2/MHDC"
FILES_${EPSM}-magic-hd-noire = "/usr/share/enigma2/Magic-HD-Noire"
FILES_${EPSM}-magic-hd-night = "/usr/share/enigma2/Magic-HD-Night/"

FILES_vix-vixbmc-hd-common = "/usr/share/enigma2/ViXBMC_HD-Common"
FILES_${EPSM}-vixbmc-slim-hd = "/usr/share/enigma2/ViXBMC_HD-Slim"
FILES_${EPSM}-vixbmc-night-hd = "/usr/share/enigma2/ViXBMC_HD-Night"
FILES_${EPSM}-vixbmc-metropolis = "/usr/share/enigma2/ViXBMC_Metropolis"

RDEPENDS_${EPSM}-day-hd = "vix-skin-common font-roboto-enigma"
RDEPENDS_${EPSM}-night-hd = "vix-skin-common font-roboto-enigma"

RDEPENDS_${EPSM}-magic-sd = "font-valis-hd"
RDEPENDS_${EPSM}-magic-hd = "font-valis-hd"
RDEPENDS_${EPSM}-magic-hd-light = "font-valis-hd ${EPSM}-magic-hd"

RDEPENDS_${EPSM}-magic-hd-noire = "font-valis-hd vix-mhdc"
RDEPENDS_${EPSM}-magic-hd-night = "font-valis-hd vix-mhdc"

RDEPENDS_${EPSM}-vixbmc-slim-hd = "vix-vixbmc-hd-common"
RDEPENDS_${EPSM}-vixbmc-night-hd = "vix-vixbmc-hd-common"
RDEPENDS_${EPSM}-vixbmc-metropolis = "vix-vixbmc-hd-common"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/* ${D}/usr/share/enigma2/
    chmod -R a+rX ${D}/usr/share/enigma2/
}
