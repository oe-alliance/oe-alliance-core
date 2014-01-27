SUMMARY = "ASIX AX8817X based USB 2.0 Ethernet Devices"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-usbnet \
    kernel-module-asix \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
