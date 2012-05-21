require linux-firmware.inc

DESCRIPTION = "Firmware for dvb-usb-dtt200u-01.fw"

SRCREV = "3aa7bab757fdb0e38c484e73d73c6d34a770c1ba"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 dvb-usb-dtt200u-01.fw ${D}${base_libdir}/firmware
}
