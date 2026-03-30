SUMMARY = "Legacy USB DVB driver package for the TBS5520 tuner"
DESCRIPTION = "Compatibility package for the original TBS5520. The TBS5520SE uses its own driver package."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
	kernel-module-dvb-usb-tbs5520 \
	kernel-module-avl6882 \
	kernel-module-r848 \
	firmware-dvb-usb-tbs5520 \
	firmware-avl6882 \
	"

PV = "1.0"
PR = "r1"

ALLOW_EMPTY:${PN} = "1"
