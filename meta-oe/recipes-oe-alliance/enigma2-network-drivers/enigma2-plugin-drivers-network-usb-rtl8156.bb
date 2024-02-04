SUMMARY = "Realtek RTL8152/RTL8153/RTL8156 USB 3.0 10/100/1GB/2.5GB Ethernet Network Adapter"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    kernel-module-usbnet \
    kernel-module-r8156 \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
