SUMMARY = "Skinpart for BMediacenter by stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.5"
PKGV = "2.5"
VER ="2.5"
PR = "r2"

SRC_URI="git://github.com/stein17/Skins-for-Plugins-by-stein17.git;protocol=https"

S = "${WORKDIR}/git/BMediacenter-AX-Blue-Skin"

FILES_${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
