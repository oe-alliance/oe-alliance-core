DESCRIPTION = "Enigma2 Skin magnum"
MAINTAINER = "OpenXTA"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"


require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r2"

SRC_URI="git://github.com/XTAv2/magnum32.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}