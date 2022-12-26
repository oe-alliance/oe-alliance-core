require linux-firmware.inc

SUMMARY = "Firmware for RTL8192CU"

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
    install -m 0644 rtlwifi/rtl8192cufw.bin ${D}/${nonarch_base_libdir}/firmware/rtlwifi/
}
