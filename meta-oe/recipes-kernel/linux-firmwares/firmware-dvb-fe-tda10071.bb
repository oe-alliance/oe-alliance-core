require linux-firmware.inc

SUMMARY = "Firmware for TDA10071 dvb frontend"

SRCREV = "3aa7bab757fdb0e38c484e73d73c6d34a770c1ba"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-tda10071.fw ${D}${nonarch_base_libdir}/firmware
}
