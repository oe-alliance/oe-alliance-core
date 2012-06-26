DESCRIPTION = "USB DVB driver for dtt200u chipsets"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "v4l-dvb"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-dtt200u \
	firmware-dvb-usb-dtt200u-01 \
	firmware-dvb-usb-wt220u-02 \
	firmware-dvb-usb-wt220u-fc03 \
	firmware-dvb-usb-wt220u-miglia-01 \
	firmware-dvb-usb-wt220u-zl0353-01 \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
