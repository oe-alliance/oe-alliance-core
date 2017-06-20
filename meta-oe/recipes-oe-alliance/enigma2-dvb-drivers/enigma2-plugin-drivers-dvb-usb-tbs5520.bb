DESCRIPTION = "USB DVB driver for TBS 5520 Tuner"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
	kernel-module-dvb-usb-tbs5520 \
	kernel-module-avl6882 \
	kernel-module-r848 \
	firmware-dvb-usb-tbs5520 \
	firmware-avl6882 \
	"

PV = "1.0"

ALLOW_EMPTY_${PN} = "1"
