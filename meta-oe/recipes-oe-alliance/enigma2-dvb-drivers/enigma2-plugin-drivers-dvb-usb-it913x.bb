SUMMARY = "USB DVB driver for it913x chipsets"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
    ${DVBPROVIDER}-module-dvb-usb-it913x \
    ${DVBPROVIDER}-module-it913x-fe \
    firmware-dvb-usb-it913x \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
