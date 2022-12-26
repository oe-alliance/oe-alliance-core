require linux-firmware.inc

SUMMARY = "Firmware for RT73"

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 rt73.bin ${D}${nonarch_base_libdir}/firmware
}
