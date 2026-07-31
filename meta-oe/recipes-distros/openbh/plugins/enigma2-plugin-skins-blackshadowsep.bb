SUMMARY = "Enigma2 Skin BlackShadowSE_P"
MAINTAINER = "OpenBh Team"
SECTION = "base"
PRIORITY = "required"
LICENSE = "LicenseRef-proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git"
PKGV = "3.0+git${GITPKGV}"
VER = "3.0"
PR = "r3"

SRC_URI = "git://github.com/BlackHole/skins.git;protocol=https;branch=master"

S = "${UNPACKDIR}/${BP}/BlackShadowSE_P"

FILES:${PN} = "/usr/*"

do_install() {
    cp -rf ${S}/usr ${D}/
}
