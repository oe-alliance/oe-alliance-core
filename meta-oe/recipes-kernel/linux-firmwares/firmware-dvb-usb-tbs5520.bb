DESCRIPTION = "Firmware for TBS 5520"
LICENSE = "CLOSED"

PR = "r0"
SRC_URI = "file://dvb-usb-tbsqbox-id5520.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

inherit allarch

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0644 dvb-usb-tbsqbox-id5520.fw ${D}${base_libdir}/firmware
}
