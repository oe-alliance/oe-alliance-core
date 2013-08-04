require linux-firmware.inc

DESCRIPTION = "Firmware for Atheros HTC devices ar9271"

SRCREV = "a3ffb1a98408f713ff82d59ae1fd203d650974c4"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 ar9271.fw ${D}${base_libdir}/firmware
	install -m 0644 LICENCE.atheros_firmware ${D}${base_libdir}/firmware/LICENCE_ar9271.txt
}
