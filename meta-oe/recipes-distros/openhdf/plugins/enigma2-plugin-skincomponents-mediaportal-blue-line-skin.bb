SUMMARY = "Skinpart for MediaPortal on OpenHDF Images by stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.2+git${SRCPV}"
PKGV = "1.2+git${GITPKGV}"
VER ="1.2"
PR = "r1"

SRC_URI="git://github.com/stein17/Mediaportal-Blue-Line-Skin.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
