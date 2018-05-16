require linux-firmware.inc

SUMMARY = "Firmware for dvb-usb-it913x"

SRCREV = "4eb21647ad89816cdc0ad90edcb486fa09ef9a31"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-it9135-01.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-it9135-02.fw ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-it9137-01.fw ${D}${nonarch_base_libdir}/firmware
}
