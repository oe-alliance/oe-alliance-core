LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://firmware-si2158.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for si2158"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0644 dvb-demod-si2168-02.fw ${D}${base_libdir}/firmware
    install -m 0644 dvb-demod-si2168-a20-01.fw ${D}${base_libdir}/firmware
    install -m 0644 dvb-demod-si2168-a30-01.fw ${D}${base_libdir}/firmware
    install -m 0644 dvb-demod-si2168-b40-01.fw ${D}${base_libdir}/firmware
    install -m 0644 dvb-tuner-si2158-a20-01.fw ${D}${base_libdir}/firmware
}
