require linux-firmware.inc

SUMMARY = "Firmware for TDA10071 dvb frontend"

SRCREV = "2c7a7f802cdf515e6e7b814111aea56669fac4b2"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-tda10071.fw ${D}${nonarch_base_libdir}/firmware
}
