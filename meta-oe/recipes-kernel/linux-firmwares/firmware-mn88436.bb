SUMMARY = "Firmware files for mn88436"
LICENSE = "CLOSED"
require conf/license/license-close.inc

inherit allarch


SRC_URI = "file://firmware-mn88436.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-mn88436-atsc.fw ${D}${nonarch_base_libdir}/firmware/dvb-fe-mn88436-atsc.fw
    install -m 0644 dvb-fe-mn88436-pseq.fw ${D}${nonarch_base_libdir}/firmware/dvb-fe-mn88436-pseq.fw
}