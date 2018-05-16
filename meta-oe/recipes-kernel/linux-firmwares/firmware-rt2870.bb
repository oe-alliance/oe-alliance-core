require linux-firmware.inc

SUMMARY = "Firmware for Ralink RT2870"

SRCREV = "bf9f8648fdf1d1d63db471554781f897d219bd62"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 rt2870.bin ${D}${nonarch_base_libdir}/firmware
}
