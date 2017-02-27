SUMMARY = "Driver for Ralink USB devices RT2571W, RT2573 & RT2671"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "rt73", "kernel-module-rt73usb", d)} \
    firmware-rt73 \
    "

PV = "1.0"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
