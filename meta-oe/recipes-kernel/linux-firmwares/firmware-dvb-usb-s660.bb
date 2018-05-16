require linux-firmware.inc

SUMMARY = "Firmware for DVB USB TeVii S660 adapter"

SRCREV = "2c7a7f802cdf515e6e7b814111aea56669fac4b2"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-usb-s660.fw ${D}${nonarch_base_libdir}/firmware
}
