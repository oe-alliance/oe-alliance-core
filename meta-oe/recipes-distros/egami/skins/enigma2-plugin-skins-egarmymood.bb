SUMMARY = "Enigma2 Skin EGAMI-Skin"
MAINTAINER = "EGAMI"
require conf/license/license-gplv2.inc
PACKAGE_ARCH = "all"

inherit gitpkgv

EPSM = "enigma2-plugin-skins.egami"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r3"

PACKAGES = "${EPSM}-egarmymoodblue"
PROVIDES = "${EPSM}-egarmymoodblue"

SRC_URI="git://github.com/a4tech/Egami-skins.git;protocol=git"

FILES_${EPSM}-egarmymoodblue = "/usr/*"

S = "${WORKDIR}/git"

do_install() {
    cp -rp ${S}/EGArmyMood/usr ${D}/
}