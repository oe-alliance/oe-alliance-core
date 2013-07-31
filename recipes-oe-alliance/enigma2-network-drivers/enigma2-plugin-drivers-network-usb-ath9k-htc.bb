DESCRIPTION = "WiFi devices for for Atheros AR9001 and AR9002 devices."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
	kernel-module-ath9k-htc \
	firmware-htc7010 \
	firmware-htc9271 \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
