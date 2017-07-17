SUMMARY = "WiFi devices for Realtek 8723bs chipsets."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = "\
    rt8723bs \
"

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
