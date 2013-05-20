LICENSE = "CLOSED"

DESCRIPTION = "Firmware for dvb-usb-af9035-02"
PACKAGE_ARCH = "all"

SRC_URI = "file://dvb-usb-af9035-02.fw.tar.gz"

S = "${WORKDIR}"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 dvb-usb-af9035-02.fw ${D}${base_libdir}/firmware
}

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"
