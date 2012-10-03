DESCRIPTION = "Xtrend-Support Panel"
MAINTAINER = "pcd"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "6.0+git${SRCPV}"
PKGV = "6.0+git${GITPKGV}"
VER ="6.0"
PR = "r0"


SRC_URI="git://github.com/xtrend-support/panel.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
	cp -rp ${S}/usr ${D}/
}