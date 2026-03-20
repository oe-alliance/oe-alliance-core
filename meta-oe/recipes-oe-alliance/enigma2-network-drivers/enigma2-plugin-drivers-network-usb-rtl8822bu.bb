SUMMARY = "WiFi devices for Realtek 88x2BU chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
        ${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "rtl88x2bu", "rt8822bu", d)} \
        firmware-rtl8192eu \
"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY:${PN} = "1"
