SUMMARY = "Single-Chip IEEE 802.11b/g/n WLAN Controller with USB 2.0 Interface"
inherit oea-wifi-driver

# wifi_driver() picks the driver from this machine's kernel, so this is machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

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
