LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://dvb-usb-af9035-02.fw-30092013.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for dvb-usb-af9035-02"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/dvb-usb-af9035-02.fw ${D}${nonarch_base_libdir}/firmware
}
