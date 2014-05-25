SUMMARY = "SpecialJump - Fast manual skipping of commercials, and more..."
MAINTAINER = "www.opena.tv Fischreiher"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.0+git${SRCPV}"
PKGV = "0.0+git${GITPKGV}"
VER ="0.0"
PR = "r0"

SRC_URI="git://github.com/openatv/SpecialJump.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"


do_install() {
    cp -rp ${S}/usr ${D}/
}