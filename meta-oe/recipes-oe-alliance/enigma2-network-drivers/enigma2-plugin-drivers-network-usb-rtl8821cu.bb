SUMMARY = "WiFi devices for Realtek 88x1CU chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
        rt8821cu \
		linux-firmware-8821cu \
		${@bb.utils.contains_any("MACHINE", "inihdp sf8008", "", "rtk-btusb", d)} \
"

PV = "1.0"
PR = "r3"

ALLOW_EMPTY:${PN} = "1"
