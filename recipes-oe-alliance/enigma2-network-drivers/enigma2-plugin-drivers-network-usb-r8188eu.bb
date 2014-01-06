DESCRIPTION = "WiFi devices for Realtek 8188EU chipsets."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	kernel-module-r8188eu \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"