SUMMARY = "USB DVB driver for Afatech 9015 chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-af9015 \
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
    firmware-dvb-usb-af9015 \
    firmware-dvb-fe-af9013 \
    "

PV = "1.0"
PR = "r6"

ALLOW_EMPTY_${PN} = "1"
