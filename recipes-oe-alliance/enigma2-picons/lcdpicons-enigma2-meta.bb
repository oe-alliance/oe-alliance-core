DESCRIPTION = "meta package for enigma2 lcdpicon sets"
PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY_${PN} = "1"

require conf/license/license-gplv2.inc

PV = "1.1"
PR = "r1"

DEPENDS = " \
		enigma2-plugin-display-picon-tv-sandman.ev0.28e \
		enigma2-plugin-display-picon-tv-sandman.28e \
"
