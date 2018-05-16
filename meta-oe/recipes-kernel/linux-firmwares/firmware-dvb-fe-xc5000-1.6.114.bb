require linux-firmware.inc

SUMMARY = "Firmware for xc5000"

SRCREV = "13f0b6bda7b567d29c747196aa65ad82b18651ca"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-xc5000-1.6.114.fw ${D}${nonarch_base_libdir}/firmware
}
