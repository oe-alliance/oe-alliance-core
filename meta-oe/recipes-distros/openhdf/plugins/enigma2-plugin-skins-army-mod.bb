MODULE = "Army-Mod"
SUMMARY = "HDFreaks.cc Army-Mod"
MAINTAINER = "HDFreaks"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
VER = "1.0"
PR = "r4"

SRC_URI = "git://github.com/openhdf/army-mod-skin.git;protocol=https;branch=master"

FILES:${PN} = "/usr/share"

do_install() {
	install -d ${D}/usr/share
	cp -r --no-preserve=ownership ${S}/usr/share/* ${D}/usr/share/
}
