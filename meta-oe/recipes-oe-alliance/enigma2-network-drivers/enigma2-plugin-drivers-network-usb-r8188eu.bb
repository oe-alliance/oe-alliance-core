SUMMARY = "WiFi devices for Realtek 8188EU chipsets."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-r8188eu \
    "

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"