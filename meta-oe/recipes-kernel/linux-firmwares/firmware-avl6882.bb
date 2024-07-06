SUMMARY = "Firmware files for avl6882"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch


SRC_URI = "file://dvb-demod-avl6882.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${UNPACKDIR}/dvb-demod-avl6882.fw ${D}${nonarch_base_libdir}/firmware/dvb-demod-avl6882.fw
}