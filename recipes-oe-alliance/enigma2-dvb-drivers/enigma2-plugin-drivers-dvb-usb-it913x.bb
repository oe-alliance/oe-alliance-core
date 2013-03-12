DESCRIPTION = "USB DVB driver for it913x chipsets"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-it913x \
	firmware-dvb-usb-it913x \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
