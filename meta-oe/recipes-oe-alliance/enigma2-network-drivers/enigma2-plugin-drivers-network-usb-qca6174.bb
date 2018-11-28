SUMMARY = "WiFi devices for QCA6174 chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    wlan-qcacld-20 \
    firmware-qca6174 \
    wireless-regdb \
    "

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
