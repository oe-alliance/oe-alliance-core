DESCRIPTION = "Driver for Ralink USB devices RT3070"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	rt3070 \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
