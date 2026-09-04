SUMMARY = "Driver for Ralink USB devices R8712U"
inherit allarch

require conf/license/license-gplv2.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

RRECOMMENDS:${PN} = " \
    kernel-module-r8712u \
    firmware-rtl8712u \
    "

PV = "1.0"
PR = "r1"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
