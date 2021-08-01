SUMMARY = "USB DVB driver for Panasonic mn88472/3 chipsets"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS:${PN} = " \
    ${DVBPROVIDER}-module-mn88472 \
    ${DVBPROVIDER}-module-mn88473 \
    firmware-mn8847x \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"
