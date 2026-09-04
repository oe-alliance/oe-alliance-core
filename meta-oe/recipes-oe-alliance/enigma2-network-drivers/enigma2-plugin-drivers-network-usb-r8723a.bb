SUMMARY = "WiFi devices for Realtek RTL8723a chipsets."
inherit allarch oea-wifi-driver

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    ${@wifi_driver(d, 'rtl8xxxu', 'kernel-module-rtl8xxxu', 'rt8723a')} \
    firmware-rtl8723a \
"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
