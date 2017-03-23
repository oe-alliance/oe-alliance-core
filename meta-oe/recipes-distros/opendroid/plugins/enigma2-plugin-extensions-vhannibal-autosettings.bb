DESCRIPTION = "vhannibal autosettings plugin"
MAINTAINER = "opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.3+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="1.3"
PR = "r2"

SRC_URI="git://github.com/opendroid-Team/vhannibal-autosettings.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
}
