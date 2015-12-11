SUMMARY = "Single-Chip IEEE 802.11b/g/n 2T2R WLAN Controller with USB 2.0 Interface"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    rtl8192eu \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
