require linux-firmware.inc

DESCRIPTION = "Firmware for dvb-usb-af9035-02"

SRCREV = "f640c0ec14a7075eed9901808e313db1623fdcc0"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 dvb-usb-af9035-02.fw ${D}${base_libdir}/firmware
}
