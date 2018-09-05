SUMMARY = "WiFi devices for QCA6174 chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    wlan-qcacld-20 \
    firmware-qca6174 \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
