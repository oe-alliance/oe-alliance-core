DESCRIPTION = "Driver for ZyDAS ZD1211, ZyDAS ZD1211B, Atheros AR5007UG"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	kernel-module-zd1211rw \
	firmware-zd1211 \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
