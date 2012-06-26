DESCRIPTION = "USB DVB driver for pctv452e chipsets"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "v4l-dvb"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-pctv452e \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
