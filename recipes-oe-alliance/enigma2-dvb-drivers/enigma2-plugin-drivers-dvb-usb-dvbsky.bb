SUMMARY = "USB DVB driver for DVB-SKY S960/S860"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RDEPENDS_${PN} = " \
    kernel-module-dvb-usb-dvbsky \
    kernel-module-m88ds3103 \
    kernel-module-dvb-usb-v2 \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
