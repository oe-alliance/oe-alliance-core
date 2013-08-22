DESCRIPTION = "Xtrend-Alliance Panel"
MAINTAINER = "Xtrend-Team"
SECTION = "base"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r2"


SRC_URI="git://github.com/xtrend-alliance/xt-panel.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
	cp -rp ${S}/usr ${D}/
	chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/XTPanel
}