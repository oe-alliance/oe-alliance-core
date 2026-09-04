SUMMARY = "Single-Chip IEEE 802.11b/g/n 2T2R WLAN Controller with PCIe Interface"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    kernel-module-rtl8192ce \
    firmware-rtl8192cu \
    firmware-rtl8712u \
    firmware-rtl8192cufw \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
