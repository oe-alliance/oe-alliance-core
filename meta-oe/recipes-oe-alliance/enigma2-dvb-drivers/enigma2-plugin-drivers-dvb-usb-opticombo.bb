SUMMARY = "OPTI-Combo DVB-T2/C USB Stick"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER = "dvb-sky"
DVBPROVIDER_dags1 = "kernel"
DVBPROVIDER_dags2 = "kernel"
DVBPROVIDER_dags3 = "kernel"
DVBPROVIDER_dags4 = "kernel"

RRECOMMENDS_${PN} = " \
    ${@base_contains("MACHINE_FEATURES", "legacykernel", "", " \
    ${DVBPROVIDER}-module-dvb-usb-dvbsky \
    ${DVBPROVIDER}-module-dvb-usb-v2 \
    ${DVBPROVIDER}-module-dvbsky-m88ds3103 \
    ${DVBPROVIDER}-module-dvbsky-m88rs6000 \
    ${DVBPROVIDER}-module-sit2fe \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    ", d)}"

PV = "2.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
