DESCRIPTION = "Xtrend-Alliance Panel"
MAINTAINER = "pcd"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"


SRC_URI="git://github.com/xtrend-alliance/xt-panel.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
	cp -rp ${S}/usr ${D}/
	chmod -R 777 ${D}/lib/enigma2/python/SystemPlugins/XTPanel
}