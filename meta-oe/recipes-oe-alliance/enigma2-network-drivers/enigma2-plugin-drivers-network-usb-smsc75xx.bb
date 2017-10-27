SUMMARY = "SMSC75XX USB 2.0 Gigabit Ethernet Devices"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-usbnet \
    kernel-module-smsc75xx \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
