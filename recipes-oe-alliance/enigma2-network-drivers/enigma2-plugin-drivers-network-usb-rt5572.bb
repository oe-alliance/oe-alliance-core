DESCRIPTION = "802.11a/b/g/n 2T2R 2.4/5 GHz USB Single Chip"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	rt5572 \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
