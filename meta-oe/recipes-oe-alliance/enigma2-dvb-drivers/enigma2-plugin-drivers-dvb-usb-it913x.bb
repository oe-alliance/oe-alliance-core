SUMMARY = "USB DVB driver for it913x chipsets"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-it913x \
    ${DVBPROVIDER}-module-it913x-fe \
    ${DVBPROVIDER}-module-af9033 \
    ${DVBPROVIDER}-module-dvb-usb-af9035 \
    ${DVBPROVIDER}-module-it913x \
    firmware-dvb-usb-it913x \
    firmware-dvb-usb-af9035-02 \
    firmware-dvb-usb-af9035-01 \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
