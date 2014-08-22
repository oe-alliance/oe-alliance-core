SUMMARY = "USB DVB driver for Realtek RTL2832 chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-rtl2832 \
    ${DVBPROVIDER}-module-dvb-usb-rtl28xxu \
    ${DVBPROVIDER}-module-rtl2832 \
    ${DVBPROVIDER}-module-e4000 \
    ${DVBPROVIDER}-module-r820t \
    ${DVBPROVIDER}-module-mt2266 \
    ${DVBPROVIDER}-module-fc0013 \
    firmware-dvb-usb-af9035-01 \
    firmware-dvb-usb-af9035-02 \
    firmware-dvb-usb-af9015 \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
