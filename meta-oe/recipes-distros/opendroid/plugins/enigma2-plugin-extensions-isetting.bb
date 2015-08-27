DESCRIPTION = "enigma2-plugin-extensions-isetting"
MAINTAINER = "opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
VER ="3.3.4"
PR = "r4"

SRC_URI="git://github.com/opendroid-Team/enigma2-plugin-extensions-isetting.git"

S = "${WORKDIR}/git"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    cp -rp ${S}/usr ${D}/
}
FILES_${PN} = "/"
