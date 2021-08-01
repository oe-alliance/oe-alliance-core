SUMMARY = "WiFi devices for QCA6174 chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    wlan-qcacld-20 \
    firmware-qca6174 \
    wireless-regdb-static \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY:${PN} = "1"
