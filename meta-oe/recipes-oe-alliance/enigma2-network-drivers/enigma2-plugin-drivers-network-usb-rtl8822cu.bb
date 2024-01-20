SUMMARY = "WiFi devices for Realtek 88x2CU chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
        rt8822cu \
        firmware-rtk8822cbtusb \
"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
