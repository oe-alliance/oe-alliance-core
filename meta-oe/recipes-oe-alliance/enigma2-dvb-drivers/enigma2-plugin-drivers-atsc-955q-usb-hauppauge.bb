SUMMARY = "USB ATSC driver for Hauppauge 955Q WinTV-HVR Tuners"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-cx231xx \
    kernel-module-cx231xx-dvb \
    kernel-module-cx25840 \
    kernel-module-lgdt3306a \
    kernel-module-si2157 \
    kernel-module-tveeprom \
    firmware-cx231xx \
    "

PV = "1.0"

ALLOW_EMPTY_${PN} = "1"
