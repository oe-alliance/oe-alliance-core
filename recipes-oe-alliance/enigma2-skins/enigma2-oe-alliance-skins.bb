DESCRIPTION = "oe-alliance skins for Enigma2"
MAINTAINER = "oe-alliance"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r2"

PACKAGES = "${EPSM}-neonovum-hd ${EPSM}-mynovum-hd"
PROVIDES = "${PN} ${EPSM}-neonovum-hd ${EPSM}-mynovum-hd"

SRC_URI="git://github.com/oe-alliance/oe-alliance-skins.git;protocol=git"

FILES_${EPSM}-neonovum-hd = "/usr/share/enigma2/NeoNOVUM_HD"
FILES_${EPSM}-mynovum-hd = "/usr/share/enigma2/myNOVUM_HD"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share/enigma2
	cp -rp ${S}/* ${D}/usr/share/enigma2/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
