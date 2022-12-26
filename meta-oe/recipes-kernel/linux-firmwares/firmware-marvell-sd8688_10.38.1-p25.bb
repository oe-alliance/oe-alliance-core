require linux-firmware.inc

SUMMARY = "Firmware for Marvell 8688 sdio wifi/bt chipset"

SRCREV = "bf9f8648fdf1d1d63db471554781f897d219bd62"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 libertas/sd8688.bin libertas/sd8688_helper.bin ${D}${nonarch_base_libdir}/firmware
    install -m 0644 LICENCE.Marvell ${D}${nonarch_base_libdir}/firmware/LICENCE_sd8688.txt
}
