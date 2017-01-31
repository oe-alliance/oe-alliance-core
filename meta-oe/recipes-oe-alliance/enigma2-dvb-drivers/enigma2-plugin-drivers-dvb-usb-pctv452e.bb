SUMMARY = "USB DVB driver for pctv452e chipsets"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-pctv452e \
    "

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
