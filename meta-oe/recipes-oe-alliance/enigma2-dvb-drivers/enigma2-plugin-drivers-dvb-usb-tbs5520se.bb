DESCRIPTION = "USB DVB driver for TBS 5520SE multi-standard tuner"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
	kernel-module-dvb-usb-tbs5520se \
	kernel-module-si2183 \
	kernel-module-av201x \
	kernel-module-si2157 \
	firmware-dvb-usb-tbs5520se \
	"

PV = "1.0"

ALLOW_EMPTY:${PN} = "1"
