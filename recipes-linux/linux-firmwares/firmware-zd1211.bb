require linux-firmware.inc

DESCRIPTION = "Firmware for ZD1211"

SRCREV = "bf9f8648fdf1d1d63db471554781f897d219bd62"

do_install() {
	install -d ${D}${base_libdir}/firmware/zd1211
	install -m 0644 zd1211/* ${D}/${base_libdir}/firmware/zd1211/
}
