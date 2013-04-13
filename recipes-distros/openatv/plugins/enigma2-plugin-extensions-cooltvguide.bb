DESCRIPTION = "CoolTVGuide MultiEPG"
MAINTAINER = "Coolman <coolman@uni.de>"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "6.1+git${SRCPV}"
PKGV = "6.1+git${GITPKGV}"
VER ="6.1"
PR = "r1"


SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-cooltvguide.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
	cp -rp ${S}/usr ${D}/
}