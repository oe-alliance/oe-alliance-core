SUMMARY = "Driver for Ralink RT8070/3070/3370/5370/5372 USB 802.11abgn WiFi sticks"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rt2800usb", "rt3070", d)} \
    firmware-rt3070"

PV = "1.0"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
