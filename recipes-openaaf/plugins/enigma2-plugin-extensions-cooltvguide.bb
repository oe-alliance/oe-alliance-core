DESCRIPTION = "CoolTVGuide MultiEPG"
MAINTAINER = "Coolman <coolman@uni.de>"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "5.6.2+git${SRCPV}"
PKGV = "5.6.2+git${GITPKGV}"
VER ="5.6.2"
PR = "r0"


SRC_URI="git://github.com/openaaf/enigma2-plugin-extensions-cooltvguide.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
	cp -rp ${S}/usr ${D}/
}