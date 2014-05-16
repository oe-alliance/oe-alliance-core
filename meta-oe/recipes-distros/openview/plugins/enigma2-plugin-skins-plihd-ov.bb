SUMMARY = "Enigma2 Skin OpenView"
MAINTAINER = "OpenView"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"


require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r1"

SRC_URI="git://github.com/openview-e2/default-skin2.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}