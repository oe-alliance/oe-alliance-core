SUMMARY = "USB DVB driver for Afatech 9035 chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-af9035 \
    ${DVBPROVIDER}-module-af9033 \
    ${DVBPROVIDER}-module-tua9001 \
    ${DVBPROVIDER}-module-mxl5007t \
    ${DVBPROVIDER}-module-tda18218 \
    firmware-dvb-usb-af9035-01 \
    firmware-dvb-usb-af9035-02 \
    firmware-dvb-usb-it913x \
    "

PV = "1.0"
PR = "r4"

ALLOW_EMPTY_${PN} = "1"
