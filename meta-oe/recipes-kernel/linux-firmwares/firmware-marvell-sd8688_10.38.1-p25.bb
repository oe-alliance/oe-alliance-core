require linux-firmware.inc

SUMMARY = "Firmware for Marvell 8688 sdio wifi/bt chipset"

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 libertas/sd8688.bin libertas/sd8688_helper.bin ${D}${nonarch_base_libdir}/firmware
    install -m 0644 LICENCE.Marvell ${D}${nonarch_base_libdir}/firmware/LICENCE_sd8688.txt
}
