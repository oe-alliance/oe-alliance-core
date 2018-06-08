SUMMARY = "USB DVB driver for pctv452e chipsets"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-pctv452e \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
