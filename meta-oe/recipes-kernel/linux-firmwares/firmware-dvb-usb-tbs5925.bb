DESCRIPTION = "Firmware for TBS 5925"
LICENSE = "CLOSED"

PR = "r1"
SRC_URI = "file://dvb-usb-tbsqbox-id5925.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-tbsqbox-id5925.fw ${D}${nonarch_base_libdir}/firmware
}
