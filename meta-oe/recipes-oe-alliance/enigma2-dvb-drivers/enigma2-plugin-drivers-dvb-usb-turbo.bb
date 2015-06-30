SUMMARY = "USB DVB driver for Vuplus Tuner Turbo"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb \
    kernel-module-dvb-usb-v2 \
    kernel-module-cypress-firmware \
    vuplus-tuner-turbo \
    "

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
