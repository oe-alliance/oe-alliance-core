SUMMARY = "USB DVB driver for Vuplus Tuner Turbo"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb \
    kernel-module-dvb-usb-v2 \
    kernel-module-cypress-firmware \
    ${@bb.utils.contains('KERNEL_VERSION', '3.14.28-1.8', 'kernel-module-cypress-firmware', '', d)} \
    ${@bb.utils.contains('KERNEL_VERSION', '3.13.5', 'kernel-module-cypress-firmware', '', d)} \
    ${@bb.utils.contains('KERNEL_VERSION', '3.9.6', 'kernel-module-dvb-usb-cypress-firmware', '', d)} \
    vuplus-tuner-turbo \
    "

PV = "1.0"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
