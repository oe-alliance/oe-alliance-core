DESCRIPTION = "Firmware for TBS 5520"
LICENSE = "CLOSED"

SRC_URI = "file://dvb-usb-tbsqbox-id5520.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-tbsqbox-id5520.fw ${D}${nonarch_base_libdir}/firmware
}
