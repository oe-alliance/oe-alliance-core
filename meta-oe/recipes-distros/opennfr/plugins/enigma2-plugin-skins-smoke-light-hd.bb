SUMMARY = "Enigma2 Skin Smoke-Light-HD"
MAINTAINER = "opennfr-stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="2.1"
PR = "r1"
SRC_URI="git://github.com/carlo0815/Smoke-Light-HD.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
