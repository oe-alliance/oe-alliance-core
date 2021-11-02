SUMMARY = "HD Skinpart for Mediaportal by stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0"
PKGV = "1.0"
VER ="1.0"
PR = "r2"

SRC_URI="git://github.com/stein17/Skins-for-Plugins-by-stein17.git;protocol=https"

S = "${WORKDIR}/git/Mediaportal-Smoke-HD-Skin"

FILES_${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
