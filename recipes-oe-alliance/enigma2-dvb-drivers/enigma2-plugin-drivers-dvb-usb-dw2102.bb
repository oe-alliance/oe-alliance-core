DESCRIPTION = "USB DVB driver for DW210x/DW310x chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-dw2102 \
	firmware-dvb-usb-s660 \
	firmware-dvb-fe-ds3000 \
	firmware-dvb-fe-ds3xxx \
	"

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
