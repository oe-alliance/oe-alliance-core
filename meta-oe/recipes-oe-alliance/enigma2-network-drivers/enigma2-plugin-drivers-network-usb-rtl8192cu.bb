SUMMARY = "Single-Chip IEEE 802.11b/g/n WLAN Controller with USB 2.0 Interface"
inherit allarch oea-wifi-driver

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    ${@wifi_driver(d, 'rtl8xxxu rtl8192cu', 'kernel-module-rtl8xxxu kernel-module-rtl8192cu', 'rtl8192cu')} \
    firmware-rtl8192cu \
    firmware-rtl8192cufw \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
