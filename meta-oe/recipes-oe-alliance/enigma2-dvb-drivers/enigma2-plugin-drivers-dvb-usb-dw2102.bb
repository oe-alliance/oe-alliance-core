SUMMARY = "USB DVB driver for DW210x/DW310x chipset"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-dw2102 \
    kernel-module-stb6100 \
    kernel-module-stv090x \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
