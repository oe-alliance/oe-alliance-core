MODULE = "Army-Mod"
SUMMARY = "HDFreaks.cc Army-Mod"
MAINTAINER = "HDFreaks"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="1.0"
PR = "r2"

SRC_URI="git://github.com/openhdf/army-mod-skin.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
        cp -rp ${S}/usr ${D}/
}
