SUMMARY = "Enigma2 Skin PAX"
MAINTAINER = "openmips"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "4.3+git${SRCPV}"
PKGV = "4.3+git${GITPKGV}"
VER ="4.3"
PR = "r0"

SRC_URI="git://github.com/openmips/skin-pax.git;protocol=git;branch=4.3"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}