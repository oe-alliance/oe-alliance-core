SUMMARY = "USB DVB driver for Afatech 867 chipset"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-a867 \
    firmware-dvb-usb-af9035-02 \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
