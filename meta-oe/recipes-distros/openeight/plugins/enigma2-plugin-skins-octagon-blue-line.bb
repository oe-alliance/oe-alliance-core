SUMMARY = "Skin Full HD for OpenEight Images by stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "open"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"
VER ="1.1"
PR = "r1"

SRC_URI="git://github.com/stein17/Blue-Line-OE.git"

S = "${WORKDIR}/git/Blue\ Line\ OE"

FILES_${PN} = "/usr"

do_package_qa[noexec] = "1"

do_install() {
    cp -rp ${S}/usr ${D}/
}
