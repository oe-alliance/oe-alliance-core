SUMMARY = "Skinpart for Full HD Skin for NFR Images by stein17"
MAINTAINER = "opennfr"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r1"

SRC_URI="git://github.com/n3wb13/BMediacenter-AX--Blue-Skin.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
