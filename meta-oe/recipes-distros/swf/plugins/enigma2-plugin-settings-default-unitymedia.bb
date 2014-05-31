SUMMARY = "swf default UnityMedia Settings "
MAINTAINER = "SWF"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
VER ="3.0"
PR = "r0"

SRC_URI="git://github.com/mcron/enigma2-plugin-settings-default-unitymedia.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/etc/*"


do_install() {
	cp -rp ${S}/etc ${D}/
}
