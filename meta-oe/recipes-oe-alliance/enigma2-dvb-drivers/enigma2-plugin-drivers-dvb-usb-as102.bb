SUMMARY = "USB DVB driver for AS102 chipset"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-as102 \
    ${DVBPROVIDER}-module-as102-fe \
    firmware-as102-data1-st \
    firmware-as102-data2-st \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
