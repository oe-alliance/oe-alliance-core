SUMMARY = "Enigma2 Skin MX_Slim-Line_NP"
MAINTAINER = "OpenBh Team"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git"
PKGV = "3.0+git${GITPKGV}"
VER ="3.0"
PR = "r3"

SRC_URI="git://github.com/BlackHole/skins.git;protocol=https;branch=master"

S = "${WORKDIR}/git/MX_Slim-Line_NP"

FILES:${PN} = "/usr/*"

do_install() {
    cp -rf ${S}/usr ${D}/
}
