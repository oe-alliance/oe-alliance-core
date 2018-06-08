SUMMARY = "USB DVB driver for EM28xx chipset"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-em28xx-dvb \
    ${DVBPROVIDER}-module-tda10071 \
    ${DVBPROVIDER}-module-cxd2820r \
    firmware-dvb-fe-tda10071 \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
