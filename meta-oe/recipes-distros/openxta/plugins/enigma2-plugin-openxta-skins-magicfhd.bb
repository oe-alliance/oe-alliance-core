SUMMARY = "Enigma2 Skin Magic-FHD for OpenXTA image"
MAINTAINER = "Rob van der Does @ OpenXTA"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "proprietary"


require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="1.0"
PR = "r0"

SRC_URI="git://github.com/XTAv2/Magic-FHD.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}