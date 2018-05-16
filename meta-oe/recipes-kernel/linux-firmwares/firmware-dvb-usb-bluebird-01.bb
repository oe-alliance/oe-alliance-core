require linux-firmware.inc

SUMMARY = "Firmware for dvb-usb-bluebird-01"

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-bluebird-01.fw ${D}${nonarch_base_libdir}/firmware
}
