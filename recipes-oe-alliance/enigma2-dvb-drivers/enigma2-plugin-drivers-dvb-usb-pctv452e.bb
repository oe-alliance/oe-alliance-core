DESCRIPTION = "USB DVB driver for pctv452e chipsets"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-pctv452e \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
