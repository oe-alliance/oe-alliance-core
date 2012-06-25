DESCRIPTION = "USB DVB driver for DW210x/DW310x chipset"

require conf/license/openvix-gplv2.inc

DVBPROVIDER ?= "v4l-dvb"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-dw2102 \
	firmware-dvb-usb-s660 \
	firmware-dvb-fe-ds3000 \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
