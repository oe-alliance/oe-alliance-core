SUMMARY = "USB ATSC driver for Hauppauge WinTV-HVR Tuners"
inherit allarch

require conf/license/license-gplv2.inc

DVBPROVIDER ?= "kernel"

RRECOMMENDS_${PN} = " \
	${DVBPROVIDER}-module-au0828 \
	${DVBPROVIDER}-module-au8522 \
	${DVBPROVIDER}-module-au8522-common \
	${DVBPROVIDER}-module-au8522-decoder \
	${DVBPROVIDER}-module-au8522-dig \
	${DVBPROVIDER}-module-cx231xx \
	${DVBPROVIDER}-module-cx231xx-alsa \
	${DVBPROVIDER}-module-cx231xx-dvb \
	${DVBPROVIDER}-module-cx2341x \
	${DVBPROVIDER}-module-em28xx \
	${DVBPROVIDER}-module-lgdt3305 \
	${DVBPROVIDER}-module-tda18271 \
	${DVBPROVIDER}-module-tda18271c2dd \
	${DVBPROVIDER}-module-tveeprom \
	${DVBPROVIDER}-module-xc5000 \
	firmware-xc5000 \
	"

PV = "1.1"

ALLOW_EMPTY_${PN} = "1"
