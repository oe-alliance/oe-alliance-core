DESCRIPTION = "WiFi devices for Ralink RT2500USB/RT2571 devices."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	kernel-module-rt2500usb \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
