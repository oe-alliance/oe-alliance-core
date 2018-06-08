SUMMARY = "Driver for Ralink USB devices R8712U"
inherit allarch

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)} \
    firmware-rtl8712u \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
