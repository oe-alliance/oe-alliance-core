SUMMARY = "USB DVB driver for Afatech 9035 chipset"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-af9035 \
    ${DVBPROVIDER}-module-af9033 \
    ${DVBPROVIDER}-module-tua9001 \
    ${DVBPROVIDER}-module-mxl5007t \
    ${DVBPROVIDER}-module-tda18218 \
    kernel-module-it913x \
    kernel-module-fc0012 \
    kernel-module-fc2580 \
    kernel-module-fc0011 \
    kernel-module-dvb-usb-v2 \
    firmware-dvb-usb-af9035-01 \
    firmware-dvb-usb-af9035-02 \
    firmware-dvb-usb-it913x \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
