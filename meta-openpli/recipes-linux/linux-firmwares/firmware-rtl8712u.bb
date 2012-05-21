require linux-firmware.inc

DESCRIPTION = "Firmware for RTL8712U"

SRCREV = "bf9f8648fdf1d1d63db471554781f897d219bd62"

do_install() {
	install -d ${D}${base_libdir}/firmware/rtlwifi
	install -m 0644 rtlwifi/rtl8712u.bin ${D}/${base_libdir}/firmware/rtlwifi/
}
