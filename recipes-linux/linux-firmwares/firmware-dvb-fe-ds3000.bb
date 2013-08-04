require linux-firmware.inc

DESCRIPTION = "Firmware for ds3000 dvb frontend"

SRCREV = "2c7a7f802cdf515e6e7b814111aea56669fac4b2"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 dvb-fe-ds3000.fw ${D}${base_libdir}/firmware
}
