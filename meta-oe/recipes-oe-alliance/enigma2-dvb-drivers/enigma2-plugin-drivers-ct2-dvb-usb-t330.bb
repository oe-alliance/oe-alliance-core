SUMMARY = "OPTI-Combo DVB-T2/C USB Stick"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb-dvbsky \
    kernel-module-si2168 \
    kernel-module-si2157 \
    firmware-dvb-fe-si2168 \
"

PV = "2.0"
PR = "r8"

ALLOW_EMPTY_${PN} = "1"
