DESCRIPTION = "enigma2-plugin-extensions-autosettings"
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
VER ="3.0"
PR = "r1"

SRC_URI="git://github.com/opendroid-Team/plugins.extension-vhannibal-autosetting.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/* \
        /etc/*"

do_install() {
cp -rp ${S}/usr ${S}/etc ${D}/
}

