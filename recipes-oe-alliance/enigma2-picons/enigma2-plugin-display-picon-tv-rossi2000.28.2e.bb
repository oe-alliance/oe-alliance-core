DESCRIPTION = "Rossi2000 28E vfd picons"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Rossi2000"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

SRC_URI[md5sum] = "7051590c63a424c8f11659df2adfd96e"
SRC_URI[sha256sum] = "adacab0bfca61750ced68225cccbe49b9c9dba4fa0a4d6e1f2f54c77893947d2"

RCONFLICTS = "enigma2-plugin-display-picon-tv-sandman.28e"
RREPLACES = "enigma2-plugin-display-picon-tv-sandman.28e"
RCONFLICTS = "enigma2-plugin-display-picon-tv-sandman.ev0.28e"
RREPLACES = "enigma2-plugin-display-picon-tv-sandman.ev0.28e"

SRCDATE = "20130313"
PV = "${SRCDATE}"
PR = "r1"
SETTINGS_FILENAME = "rossi2000_28.2e"

SRC_URI = "http://enigma2.world-of-satellite.com/picons/${SETTINGS_FILENAME}_${PV}.tgz"

S = "${WORKDIR}/lcd_picon"

inherit autotools pkgconfig

PACKAGES = "${PN}"

FILES_${PN} = "/lcd_picon"

do_install() {
	install -d ${D}/lcd_picon
	install -m 0644 ${WORKDIR}/lcd_picon/*.* ${D}/lcd_picon
}

do_install_append() {
	find ${D}/lcd_picon/ -name 'picon_default.png' -exec rm {} \;
}
