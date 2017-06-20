SUMMARY = "pctv 292e tripleStick"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-em28xx \
    kernel-module-em28xx-dvb \
    kernel-module-em28xx-rc \
    kernel-module-si2168 \
    kernel-module-si2157 \
    firmware-dvb-fe-si2168 \
"

PV = "2.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
