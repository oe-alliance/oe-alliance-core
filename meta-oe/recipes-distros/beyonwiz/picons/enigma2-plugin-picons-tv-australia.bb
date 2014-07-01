SUMMARY = "Australia Picons"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Beyonwiz"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
VER ="3.0"
PR = "r5"

SRC_URI="git://bitbucket.org/beyonwiz/picons-australia.git;protocol=https"

S = "${WORKDIR}/git"

PACKAGES = "${PN}"

FILES_${PN} = "/picon/*"

do_install() {
	cp -rp ${S}/* ${D}/
}