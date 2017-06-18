SUMMARY = "FHD Skin for Mediaportal by stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "open"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"
VER ="1.1"
PR = "r1"

SRC_URI="git://github.com/stein17/Mediaportal-Blue-Line-Skin.git"

S = "${WORKDIR}/git/"

FILES_${PN} = "/usr"

do_package_qa[noexec] = "1"

do_install() {
    cp -rp ${S}/usr ${D}/
}
