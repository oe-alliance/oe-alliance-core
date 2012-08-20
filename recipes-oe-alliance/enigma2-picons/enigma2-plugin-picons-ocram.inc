SECTION = "base"
LICENSE = "proprietary"
MAINTAINER = "ViX team"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRCDATE = "2012-08-19--11-41-11"
PRIORITY = "required"
PV = "${SRCDATE}"
PR = "r3"

#SRC_URI = "http://www.tipshesemans.be/piconsArchive/${PICONS_FILENAME}.tar.gz"
SRC_URI = "http://secret.mynonpublic.com/picons/${PICONS_FILENAME}.tar.gz"

S = "${WORKDIR}/picon"

inherit autotools pkgconfig

PACKAGES = "${PN}"

FILES_${PN} = "/picon"

do_install() {
	install -d ${D}/picon
	install -m 0644 ${WORKDIR}/picon/*.* ${D}/picon
}

do_install_append() {
	find ${D}/picon/ -name 'picon_default.png' -exec rm {} \;
}
