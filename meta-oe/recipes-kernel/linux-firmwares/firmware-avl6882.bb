SUMMARY = "Firmware files for avl6882"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "all"

PR = "r0"

SRC_URI = "file://dvb-demod-avl6882.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0644 ${S}/dvb-demod-avl6882.fw ${D}${base_libdir}/firmware/dvb-demod-avl6882.fw
}