DESCRIPTION = "Firmware for TBS 5980"
require conf/license/license-close.inc

SRC_URI = "file://dvb-usb-tbsqbox-id5980.zip"

S = "${UNPACKDIR}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-tbsqbox-id5980.fw ${D}${nonarch_base_libdir}/firmware
}
