require linux-firmware.inc

SUMMARY = "Firmware for xc5000"

SRCREV = "f640c0ec14a7075eed9901808e313db1623fdcc0"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-xc5000-1.6.114.fw ${D}${nonarch_base_libdir}/firmware
}
