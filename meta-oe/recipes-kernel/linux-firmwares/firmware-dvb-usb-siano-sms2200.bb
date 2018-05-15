LICENSE = "CLOSED"
require conf/license/license-close.inc
PR = "r0"
SRC_URI = "file://fw-isdbt_rio.tar.gz"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for dvb-usb-sms2200 isdbt_rio.inp"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 isdbt_rio.inp ${D}${nonarch_base_libdir}/firmware
}
