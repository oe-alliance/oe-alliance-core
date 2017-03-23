SUMMARY = "Enigma2 Skin MX_TITANIUM_C"
MAINTAINER = "BlackHole team"
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

SRC_URI="git://github.com/BlackHole/skins.git"

S = "${WORKDIR}/git/MX_Titanium_C"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}
