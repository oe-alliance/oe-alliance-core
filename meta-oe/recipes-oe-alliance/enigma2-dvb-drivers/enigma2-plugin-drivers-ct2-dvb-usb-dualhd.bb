SUMMARY = "USB driver for Hauppauge WinTV-DualHD Tuner"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-si2168 \
    ${DVBPROVIDER}-module-si2157 \
    ${DVBPROVIDER}-module-em28xx-dvb \
    ${DVBPROVIDER}-module-tveeprom \
    ${DVBPROVIDER}-module-em28xx \
    firmware-dvb-fe-si2168 \
"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
