LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://dvb-usb-af9035-02.fw-30092013.tar.gz"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for dvb-usb-af9035-02"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0644 dvb-usb-af9035-02.fw ${D}${base_libdir}/firmware
}
