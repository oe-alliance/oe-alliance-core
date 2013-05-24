DESCRIPTION = "MatrixHD skins for Enigma2"
MAINTAINER = "Steve Wheeler"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins"
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r13"

PACKAGES += "matrixhd-components"
PROVIDES += "matrixhd-components"

SRC_URI="git://github.com/scwheeler/MetrixHD-for_VIX.git;protocol=git"

FILES_matrixhd-components = "/usr/lib/enigma2/python"
FILES_${EPSM}-matrixhd = "/usr/share/enigma2/MetrixHD"

RDEPENDS_${EPSM}-matrixhd = "matrixhd-components"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/
	cp -rp ${S}/usr/* ${D}/usr/
	chmod -R a+rX ${D}/usr/
}
