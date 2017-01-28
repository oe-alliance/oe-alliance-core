DESCRIPTION = "OPENDROID-PANEL"
MAINTAINER = "opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="3.0"
PR = "r6"

SRC_URI="git://github.com/opendroid-Team/OPENDROID-PANEL.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* /etc/*"

do_install() {
cp -rp ${S}/usr ${D}/
cp -rp ${S}/etc ${D}/
}
