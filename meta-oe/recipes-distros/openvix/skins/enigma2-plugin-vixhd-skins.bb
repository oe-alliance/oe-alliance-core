SUMMARY = "OpenViX FULL HD skins by rossi2000 and simonsez"
MAINTAINER = "rossi2000"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins1080"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r3"

PACKAGES = "youvix-common ${EPSM}-youvix-blue ${EPSM}-youvix-red ${EPSM}-youvix-green ${EPSM}-youvix-purple ${EPSM}-vixbmc-1080"

SRC_URI = "git://github.com/OpenViX/vixhd-skins.git;protocol=git"

FILES_youvix-common = "/usr/share/enigma2/YouViX-Common"
FILES_${EPSM}-youvix-blue = "/usr/share/enigma2/YouViX-Blue"
FILES_${EPSM}-youvix-red = "/usr/share/enigma2/YouViX-Red"
FILES_${EPSM}-youvix-green = "/usr/share/enigma2/YouViX-Green"
FILES_${EPSM}-youvix-purple = "/usr/share/enigma2/YouViX-Purple"
FILES_${EPSM}-vixbmc-1080 = "/usr/share/enigma2/ViXBMC_1080"

RDEPENDS_${EPSM}-youvix-blue = "youvix-common"
RDEPENDS_${EPSM}-youvix-red = "youvix-common"
RDEPENDS_${EPSM}-youvix-green = "youvix-common"
RDEPENDS_${EPSM}-youvix-purple = "youvix-common"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/* ${D}/usr/share/enigma2/
    chmod -R a+rX ${D}/usr/share/enigma2/
}
