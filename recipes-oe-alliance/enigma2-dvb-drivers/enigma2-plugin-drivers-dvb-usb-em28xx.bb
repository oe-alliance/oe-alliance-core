DESCRIPTION = "USB DVB driver for EM28xx chipset"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-em28xx-dvb \
	${DVBPROVIDER}-module-tda10071 \
	firmware-dvb-fe-tda10071 \
	"

PV = "1.0"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
