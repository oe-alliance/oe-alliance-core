DESCRIPTION = "openAAF default SAT Settings 13/19/23/28"
MAINTAINER = "AAF Team"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
VER ="2.0"
PR = "r0"

SRC_URI="git://github.com/openaaf/enigma2-plugin-settings-defaultsat.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/etc/*"


do_install() {
	cp -rp ${S}/etc ${D}/
}