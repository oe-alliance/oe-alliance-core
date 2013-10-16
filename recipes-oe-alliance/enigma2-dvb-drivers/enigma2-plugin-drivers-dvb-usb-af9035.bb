DESCRIPTION = "USB DVB driver for Afatech 9035 chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-af9035 \
	firmware-dvb-usb-af9035-01 \
	firmware-dvb-usb-af9035-02 \
	"

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
