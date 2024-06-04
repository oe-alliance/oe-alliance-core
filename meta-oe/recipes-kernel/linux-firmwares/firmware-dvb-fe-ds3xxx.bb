LICENSE = "CLOSED"
require conf/license/license-close.inc

SUMMARY = "Firmware for ds3xxx dvb frontend"
inherit allarch

SRC_URI = "file://fw-ds3xxx.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0755 ${UNPACKDIR}/dvb-fe-ds300x.fw ${D}${nonarch_base_libdir}/firmware/dvb-fe-ds300x.fw
    install -m 0755 ${UNPACKDIR}/dvb-fe-ds3103.fw ${D}${nonarch_base_libdir}/firmware/dvb-fe-ds3103.fw
}

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"
