LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://firmware-si2158.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for si2158"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-demod-si2168-02.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-demod-si2168-a20-01.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-demod-si2168-a30-01.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-demod-si2168-b40-01.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-demod-si2168-d60-01.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-tuner-si2141-a10-01.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-tuner-si2158-a20-01.fw ${D}${nonarch_base_libdir}/firmware
}
