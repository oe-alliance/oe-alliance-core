require linux-firmware.inc

DESCRIPTION = "Firmware for RTL8192EU"

SRCREV = "${AUTOREV}"

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
	install -m 0644 rtlwifi/rtl8192eu_nic.bin ${D}/${nonarch_base_libdir}/firmware/rtlwifi/
}
