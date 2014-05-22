SUMMARY = "WiFi devices for Realtek 8723a chipsets."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = "rt8723a"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
