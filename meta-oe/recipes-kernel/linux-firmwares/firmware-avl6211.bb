SUMMARY = "Firmware files for avl6211"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch

PR = "r00"

SRC_URI = "file://firmware-avl6211.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-avl6211.fw ${D}${nonarch_base_libdir}/firmware/dvb-fe-avl6211.fw
}