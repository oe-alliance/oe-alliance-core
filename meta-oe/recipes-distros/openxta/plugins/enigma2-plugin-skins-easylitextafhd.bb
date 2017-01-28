SUMMARY = "Enigma2 Skin Easylite HD for OpenXTA image"
MAINTAINER = "OpenXTA"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"


require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="1.0"
PR = "r0"

SRC_URI="git://github.com/XTAv2/easylitextafhd.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}
