SUMMARY = "OPTI-Combo DVB-T2/C USB Stick"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-dvb-usb-dvbsky \
    kernel-module-ts2020 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
"


PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
