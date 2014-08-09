SUMMARY = "USB DVB driver for DVB-SKY Family"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = " \
    ${@base_contains("MACHINE_FEATURES", "legacykernel", "", " \
    kernel-module-dvb-usb-dvbsky \
    kernel-module-m88ds3103 \
    kernel-module-dvb-usb-v2 \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    ", d)}"

PV = "2.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"
