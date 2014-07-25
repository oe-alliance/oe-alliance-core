SUMMARY = "USB DVB driver for Siano chipset"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
    ${DVBPROVIDER}-module-smsusb \
    ${DVBPROVIDER}-module-smsdvb \
    ${@base_contains("MACHINE_FEATURES", "legacykernel", \
    " \
    ${DVBPROVIDER}-module-smsmdtv \
    " , "", d)} \
    firmware-dvb-siano \
    firmware-dvb-nova-12mhz-b0 \
    firmware-isdbt-nova-12mhz-b0 \
    firmware-dvb-usb-siano-sms2200 \
    "

PV = "1.0"
PR = "r8"

ALLOW_EMPTY_${PN} = "1"
