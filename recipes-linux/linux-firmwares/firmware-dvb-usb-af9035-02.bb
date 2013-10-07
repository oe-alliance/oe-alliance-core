LICENSE = "CLOSED"
PR = "r02"
SRC_URI = "file://dvb-usb-af9035-02.fw-30092013.tar.gz"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

PACKAGE_ARCH = "all"

DESCRIPTION = "Firmware for dvb-usb-af9035-02"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 dvb-usb-af9035-02.fw ${D}${base_libdir}/firmware
}
