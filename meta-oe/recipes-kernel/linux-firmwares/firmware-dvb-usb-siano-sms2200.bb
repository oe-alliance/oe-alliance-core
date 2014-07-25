LICENSE = "CLOSED"
PR = "r0"
SRC_URI = "file://fw-isdbt_rio.tar.gz"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

PACKAGE_ARCH = "all"

SUMMARY = "Firmware for dvb-usb-sms2200 isdbt_rio.inp"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0644 isdbt_rio.inp ${D}${base_libdir}/firmware
}
