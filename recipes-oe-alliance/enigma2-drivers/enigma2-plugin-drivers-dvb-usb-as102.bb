DESCRIPTION = "USB DVB driver for AS102 chipset"

require conf/license/openvix-gplv2.inc

DVBPROVIDER ?= "v4l-dvb"

RRECOMMENDS_${PN} = " \
	kernel-module-dvb-as102 \
	firmware-as102-data1-st \
	firmware-as102-data2-st \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
