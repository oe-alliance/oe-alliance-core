require linux-firmware.inc

SUMMARY = "Firmware for Atheros HTC devices ar9271"

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ar9271.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 LICENCE.atheros_firmware ${D}${nonarch_base_libdir}/firmware/LICENCE_ar9271.txt
}
