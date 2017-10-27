SUMMARY = "USB DVB driver for Siano chipset"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
    ${DVBPROVIDER}-module-smsusb \
    ${DVBPROVIDER}-module-smsdvb \
    ${@bb.utils.contains("MACHINE_FEATURES", "legacykernel", \
    " \
    ${DVBPROVIDER}-module-smsmdtv \
    " , "", d)} \
    firmware-dvb-siano \
    firmware-dvb-nova-12mhz-b0 \
    firmware-isdbt-nova-12mhz-b0 \
    firmware-dvb-usb-siano-sms2200 \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
