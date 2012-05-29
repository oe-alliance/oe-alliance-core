DESCRIPTION = "USB DVB driver for Siano chipset"

require conf/license/openvix-gplv2.inc

DVBPROVIDER ?= "v4l-dvb"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-smsusb \
	${DVBPROVIDER}-module-smsdvb \
	firmware-dvb-siano \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

