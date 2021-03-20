SUMMARY = "WiFi devices for Realtek 88x1CU chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
        rt8821cu \
		linux-firmware-8821cu \
		${@bb.utils.contains("MACHINE", "inihdp", "", "rtk-btusb", d)} \
"

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
