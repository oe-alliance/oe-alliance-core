DESCRIPTION = "USB DVB driver for dtt200u chipsets"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-dtt200u \
	firmware-dvb-usb-dtt200u-01 \
	firmware-dvb-usb-wt220u-02 \
	firmware-dvb-usb-wt220u-fc03 \
	firmware-dvb-usb-wt220u-miglia-01 \
	firmware-dvb-usb-wt220u-zl0353-01 \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
