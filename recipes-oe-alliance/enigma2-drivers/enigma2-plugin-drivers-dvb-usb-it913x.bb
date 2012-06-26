DESCRIPTION = "USB DVB driver for it913x chipsets"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "v4l-dvb"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-it913x \
	firmware-dvb-usb-it913x \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
