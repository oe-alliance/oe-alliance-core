require linux-firmware.inc

DESCRIPTION = "Firmware for RT73"

SRCREV = "bf9f8648fdf1d1d63db471554781f897d219bd62"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 rt73.bin ${D}${base_libdir}/firmware
}
