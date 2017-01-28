DESCRIPTION = "enigma2-plugin-extensions-weatherplugin"
MAINTAINER = "opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="3.0"
PR = "r1"

SRC_URI="git://github.com/opendroid-Team/enigma2-plugin-extensions-weatherplugin.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}
