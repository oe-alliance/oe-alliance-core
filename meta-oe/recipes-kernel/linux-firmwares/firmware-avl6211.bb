SUMMARY = "Firmware files for avl6211"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch


SRC_URI = "file://firmware-avl6211.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-avl6211.fw ${D}${nonarch_base_libdir}/firmware/dvb-fe-avl6211.fw
}