require linux-firmware.inc

SUMMARY = "Firmware for xc5000c"

SRCREV = "${AUTOREV}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-xc5000c-4.1.30.7.fw ${D}${nonarch_base_libdir}/firmware
}
