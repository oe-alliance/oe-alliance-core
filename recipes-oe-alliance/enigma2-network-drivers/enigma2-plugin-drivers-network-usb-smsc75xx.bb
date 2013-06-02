DESCRIPTION = "USB Network driver for SMSC's LAN7500"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	kernel-module-smsc75xx \
	kernel-module-asix \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
