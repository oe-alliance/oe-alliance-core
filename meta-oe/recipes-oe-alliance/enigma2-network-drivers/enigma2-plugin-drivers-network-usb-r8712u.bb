SUMMARY = "Driver for Ralink USB devices R8712U"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)} \
    firmware-rtl8712u \
    "

PV = "1.0"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
