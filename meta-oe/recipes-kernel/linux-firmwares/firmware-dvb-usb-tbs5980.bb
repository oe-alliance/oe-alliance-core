DESCRIPTION = "Firmware for TBS 5980"
LICENSE = "CLOSED"

PR = "r1"
SRC_URI = "file://dvb-usb-tbsqbox-id5980.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-tbsqbox-id5980.fw ${D}${nonarch_base_libdir}/firmware
}
