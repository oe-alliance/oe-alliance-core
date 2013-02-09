DESCRIPTION = "USB DVB driver for Afatech 867 chipset"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-a867 \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
