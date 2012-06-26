DESCRIPTION = "sgtflipflop 28e"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "silverfox0786"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PR = "r1"
SRCDATE = "20120307"
PV = "${SRCDATE}"
SETTINGS_FILENAME = "sgtflipflop.28e.picons"

SRC_URI = "http://enigma2.world-of-satellite.com/picons/${SETTINGS_FILENAME}_${PV}.tar.gz"

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
