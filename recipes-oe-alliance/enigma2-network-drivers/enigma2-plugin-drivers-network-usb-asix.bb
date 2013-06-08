DESCRIPTION = "ASIX AX8817X based USB 2.0 Ethernet Devices"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	kernel-module-usbnet \
	kernel-module-asix \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
