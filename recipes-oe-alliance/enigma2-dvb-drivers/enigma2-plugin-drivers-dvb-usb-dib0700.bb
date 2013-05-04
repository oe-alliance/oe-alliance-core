DESCRIPTION = "USB DVB driver for dib0700 chipset"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
	${DVBPROVIDER}-module-dvb-usb-dib0700 \
	${DVBPROVIDER}-module-dvb-usb-dibusb-common \
	${DVBPROVIDER}-module-dvb-usb-dibusb-mc \
	${@base_contains("MACHINE_FEATURES", "legacykernel", \
	" \
	${DVBPROVIDER}-module-dib0090 \
	${DVBPROVIDER}-module-dib3000mb \
	${DVBPROVIDER}-module-dib3000mc \
	${DVBPROVIDER}-module-dibx000-common \
	${DVBPROVIDER}-module-mt2060 \
	${DVBPROVIDER}-module-dib7000m \
	${DVBPROVIDER}-module-dib7000p \
	${DVBPROVIDER}-module-dib0070 \
	${DVBPROVIDER}-module-dib8000 \
	${DVBPROVIDER}-module-dvb-usb \
	" , "", d)} \	
	firmware-dvb-usb-dib0700-1.20 \
	firmware-dvb-usb-dibusb-5.0.0.11 \
	firmware-dvb-usb-dibusb-6.0.0.8 \
	firmware-dvb-usb-dibusb-an2235-01 \
	firmware-xc3028-v27 \
	firmware-xc3028l-v36 \
	"

PV = "1.0"
PR = "r6"

ALLOW_EMPTY_${PN} = "1"
