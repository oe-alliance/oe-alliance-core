SUMMARY = "WiFi devices for Realtek 8192EU chipsets."
inherit oea-wifi-driver

# wifi_driver() picks the driver from this machine's kernel, so this is machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    ${@wifi_driver(d, 'rtl8xxxu', 'kernel-module-rtl8xxxu', 'rtl8192eu')} \
    firmware-rtl8192eu \
    "

PV = "1.0"
PR = "r3"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
