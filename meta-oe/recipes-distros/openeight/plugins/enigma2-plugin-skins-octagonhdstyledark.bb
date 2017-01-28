SUMMARY = "Enigma2 Skin Octagon-HD-Style-Dark"
MAINTAINER = "Openeight"
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
PR = "r1"

SRC_URI="git://github.com/stein17/Octagon-HD-Style-Dark.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr"

do_package_qa[noexec] = "1"

do_install() {
    cp -rp ${S}/usr ${D}/
}
