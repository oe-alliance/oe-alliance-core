SUMMARY = "WiFi devices for Realtek 8812AU/8821AU chipsets."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
        rt8812au \
"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
