DESCRIPTION = "Enigma2 Skin TechniHD"
MAINTAINER = "OpenXTA"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"


require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
VER ="2.0"
PR = "r3"

SRC_URI="git://github.com/XTAv2/TechniHD.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}
