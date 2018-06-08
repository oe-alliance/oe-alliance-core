SUMMARY = "Single-Chip IEEE 802.11b/g/n 2T2R WLAN Controller with USB 2.0 Interface"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "kernel-module-rtl8192ce kernel-module-rtl8192cu", d)} \
    firmware-rtl8192cu \
    firmware-rtl8712u \
    firmware-rtl8192cufw \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
