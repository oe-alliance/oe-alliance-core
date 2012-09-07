SECTION = "base"
LICENSE = "proprietary"
MAINTAINER = "ViX team"
PACKAGE_ARCH = "all"
PRIORITY = "optional"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/oe-alliance/ocram-picons.git;protocol=git"

S = "${WORKDIR}/git/${PICONS_FILENAME}"

inherit autotools pkgconfig

PACKAGES = "${PN}"

FILES_${PN} = "/picon"

do_install() {
	install -d ${D}/picon
	cp -P ${S}/*.* ${D}/picon
}

do_install_append() {
	find ${D}/picon/ -name 'picon_default.png' -exec rm {} \;
}
