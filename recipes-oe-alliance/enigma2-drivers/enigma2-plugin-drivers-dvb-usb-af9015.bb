DESCRIPTION = "USB DVB driver for Afatech 9015 chipset"

require conf/license/openvix-gplv2.inc

DVBPROVIDER ?= "v4l-dvb"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-af9015 \
	firmware-dvb-usb-af9015 \
	firmware-dvb-fe-af9013 \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
