SUMMARY = "Driver for Ralink RT8070/3070/3370/5370/5372 USB 802.11abgn WiFi sticks"
inherit allarch oea-wifi-driver

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    ${@wifi_driver(d, 'rt2800usb', 'kernel-module-rt2800usb', 'rt3070')} \
    firmware-rt3070"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
