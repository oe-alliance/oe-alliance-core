DESCRIPTION = "USB DVB driver for Afatech 9015 chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-af9015 \
	${@base_contains("MACHINE_FEATURES", "legacykernel", \
	" \
	${DVBPROVIDER}-module-af9013 \
	${DVBPROVIDER}-module-dvb-pll \
	${DVBPROVIDER}-module-dvb-usb \
	${DVBPROVIDER}-module-mc44s803 \
	${DVBPROVIDER}-module-mt2060 \
	${DVBPROVIDER}-module-mxl5005s \
	${DVBPROVIDER}-module-mxl5007t \
	${DVBPROVIDER}-module-qt1010 \
	${DVBPROVIDER}-module-tda18218 \
	${DVBPROVIDER}-module-tda18271 \
	" , "", d)} \
	firmware-dvb-usb-af9015 \
	firmware-dvb-fe-af9013 \
	"

PV = "1.0"
PR = "r5"

ALLOW_EMPTY_${PN} = "1"
