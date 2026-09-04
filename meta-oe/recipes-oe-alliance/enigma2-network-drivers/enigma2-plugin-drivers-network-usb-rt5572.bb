SUMMARY = "802.11a/b/g/n 2T2R 2.4/5 GHz USB Single Chip"
inherit allarch oea-wifi-driver

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    ${@wifi_driver(d, 'rt2800usb', 'kernel-module-rt2800usb', 'rt5572')} \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
