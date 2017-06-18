SUMMARY = "USB ATSC driver for Hauppauge 950Q WinTV-HVR Tuners"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-au0828 \
    kernel-module-au8522 \
    kernel-module-au8522-common \
    kernel-module-au8522-dig \
    kernel-module-tveeprom \
    kernel-module-xc5000 \
    firmware-xc5000c \
    "

PV = "1.0"

ALLOW_EMPTY_${PN} = "1"
