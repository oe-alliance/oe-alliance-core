SUMMARY = "ASIX AX88179_178A USB 3.0/2.0 Gigabit Ethernet Network Adapter"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-usbnet \
	${@bb.utils.contains("MACHINE_FEATURES", "wifiusbmodule", " \
	kernel-module-ax88179-178a", " \
    ax88179-178a \
	", d)} \
    "

PV = "1.0"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
