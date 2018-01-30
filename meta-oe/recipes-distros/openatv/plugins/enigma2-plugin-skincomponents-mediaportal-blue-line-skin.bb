SUMMARY = "Full HD Skinpart for MediaPortal on OpenATV Images by stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.2+git${SRCPV}"
PKGV = "3.2+git${GITPKGV}"
VER ="3.2"
PR = "r1"

SRC_URI="git://github.com/stein17/Skins-for-Plugins-by-stein17.git"

S = "${WORKDIR}/git/Mediaportal-Blue-Line-Skin"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
