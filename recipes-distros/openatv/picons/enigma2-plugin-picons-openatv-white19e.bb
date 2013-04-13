DESCRIPTION = "plugin-picons-openatv-white19E"
MAINTAINER = "ATV Team"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r1"


SRC_URI="git://github.com/openatv-picons/enigma2-plugin-picons-openatv-white19E.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* "

do_install() {
	cp -rp ${S}/usr ${D}/
}