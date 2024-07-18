SUMMARY = "WiFi devices for Realtek 8852CU chipsets."
inherit allarch nospdx

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
        rtl8852cu \
"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"
