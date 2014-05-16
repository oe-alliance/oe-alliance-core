SUMMARY = "Enigma2 Skin Beyonwiz"
MAINTAINER = "Beyonwiz"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r0"

SRC_URI="git://bitbucket.org/beyonwiz/skin-default.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}