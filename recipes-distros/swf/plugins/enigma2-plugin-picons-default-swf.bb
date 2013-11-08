DESCRIPTION = "SWF picons 13 19"
MAINTAINER ?= "SWF"
LICENSE = "GPL"
DEPENDS = "pngcrush-native"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "all"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
VER ="2.0"
PR = "r3"

SRC_URI="git://github.com/mcron/enigma2-plugin-picons-swf.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/picon/*.png"

do_install() {
	cp -rp ${S}/ ${D}/picon
}
