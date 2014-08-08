SUMMARY = "USB DVB driver for DVB-SKY Family"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
    dvb-sky-module-dvb-usb-dvbsky \
    dvb-sky-module-dvb-usb-v2.ipk \
    dvb-sky-module-dvbsky-m88ds3103 \
    dvb-sky-module-dvbsky-m88rs6000 \
    dvb-sky-module-sit2fe \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    "

PV = "2.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
