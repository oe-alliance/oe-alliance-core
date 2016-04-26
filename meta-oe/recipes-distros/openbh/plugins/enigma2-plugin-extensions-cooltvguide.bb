SUMMARY = "CoolTVGuide MultiEPG"
MAINTAINER = "Coolman <coolman@uni.de>"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "7.3+git${SRCPV}"
PKGV = "7.3+git${GITPKGV}"
VER ="7.3"
PR = "r1"


SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-cooltvguide.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
    cp -rp ${S}/usr ${D}/
}