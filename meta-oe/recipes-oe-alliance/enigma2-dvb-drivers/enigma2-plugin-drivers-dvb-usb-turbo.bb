SUMMARY = "USB DVB driver for Vuplus Tuner Turbo"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb \
    kernel-module-dvb-usb-v2 \
    ${DVB_CYPRESS}-cypress-firmware \
    vuplus-tuner-turbo \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
