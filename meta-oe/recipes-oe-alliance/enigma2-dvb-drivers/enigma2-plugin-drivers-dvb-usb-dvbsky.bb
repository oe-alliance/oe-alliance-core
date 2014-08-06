SUMMARY = "USB DVB driver for DVB-SKY S960/S860"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPEND = " \
    usb-dvb-dvbsky-dvbs \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    "

RDEPENDS_${PN} = " \
    dvb-sky-module-dvbs-dvb-usb-dvbsky \
    dvb-sky-module-dvbs-dvb-usb-v2.ipk \
    dvb-sky-module-dvbs-dvbsky-m88ds3103 \
    dvb-sky-module-dvbs-dvbsky-m88rs6000 \
    dvb-sky-module-dvbs-sit2fe \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    "

PV = "2.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
