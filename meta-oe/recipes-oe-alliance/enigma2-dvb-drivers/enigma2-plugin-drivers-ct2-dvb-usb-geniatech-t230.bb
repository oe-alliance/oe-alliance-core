SUMMARY = "USB geniatech driver for T230"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb-cxusb \
    kernel-module-si2168 \
    kernel-module-si2157 \
    firmware-dvb-fe-si2168 \
"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
