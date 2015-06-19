SUMMARY = "USB DVB driver for DVB-SKY Family"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

DVBPROVIDER = "dvb-sky"
DVBPROVIDER_dags1 = "kernel"
DVBPROVIDER_dags2 = "kernel"
DVBPROVIDER_dags3 = "kernel"
DVBPROVIDER_dags4 = "kernel"
DVBPROVIDER_dags5 = "kernel"

DVBPROVIDER_xc7362 = "kernel"
DVBPROVIDER_xc7358 = "kernel"
DVBPROVIDER_xc7358ci = "kernel"

DVBPROVIDER_hd500c = "kernel"
DVBPROVIDER_hd1100 = "kernel"
DVBPROVIDER_hd1200 = "kernel"
DVBPROVIDER_hd2400 = "kernel"

DVBPROVIDER_triplex = "kernel"

DVBPROVIDER_xp1000 = "kernel"

DVBPROVIDER_formuler1 = "kernel"
DVBPROVIDER_formuler3 = "kernel"

RRECOMMENDS_${PN} = " \
    ${@base_contains("MACHINE_FEATURES", "legacykernel", "", " \
    ${DVBPROVIDER}-module-dvb-usb-dvbsky \
    ${DVBPROVIDER}-module-dvb-usb-v2 \
    ${DVBPROVIDER}-module-dvbsky-m88ds3103 \
    ${DVBPROVIDER}-module-dvbsky-m88rs6000 \
    ${DVBPROVIDER}-module-sit2fe \
    ${DVBPROVIDER}-module-si2168 \
    ${DVBPROVIDER}-module-si2157 \
    firmware-dvb-usb-s660 \
    firmware-dvb-fe-ds3000 \
    firmware-dvb-fe-ds3xxx \
    firmware-dvb-fe-si2168 \
    ", d)}"

PV = "2.0"
PR = "r6"

ALLOW_EMPTY_${PN} = "1"
