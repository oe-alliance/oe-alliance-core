DESCRIPTION = "Driver for Ralink USB devices R8712U"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	${@base_contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)} \
	firmware-rtl8712u \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
