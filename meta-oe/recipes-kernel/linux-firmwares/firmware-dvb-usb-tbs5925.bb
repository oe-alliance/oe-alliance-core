DESCRIPTION = "Firmware for TBS 5925"
LICENSE = "CLOSED"

SRC_URI = "file://dvb-usb-tbsqbox-id5925.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-tbsqbox-id5925.fw ${D}${nonarch_base_libdir}/firmware
}
