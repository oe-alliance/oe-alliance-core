SUMMARY = "USB DVB driver for Edision edicombo Tuner"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb \
    edision-tuner-edicombo \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
