SUMMARY = "USB DVB driver for Vuplus Tuner Turbo"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb \
    kernel-module-dvb-usb-v2 \
    ${@base_contains('KERNEL_VERSION', '3.9.6', 'kernel-module-dvb-usb-cypress-firmware', 'kernel-module-cypress-firmware', d)} \
    vuplus-tuner-turbo \
    "

PV = "1.0"
PR = "r4"

ALLOW_EMPTY_${PN} = "1"
