SUMMARY = "WiFi devices for Realtek 8723bu chipsets."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = "rt8723bu"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
