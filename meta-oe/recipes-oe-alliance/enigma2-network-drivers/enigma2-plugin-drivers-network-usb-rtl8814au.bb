SUMMARY = "WiFi devices for Realtek 8814AU chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
        rt8814au \
"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
