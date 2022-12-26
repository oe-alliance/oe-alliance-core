require linux-firmware.inc

SUMMARY = "Firmware for xc5000"

SRCREV = "25f936b80ec14d276ef1a9ab02241b9dee750816"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 dvb-fe-xc5000-1.1.fw ${D}${nonarch_base_libdir}/firmware
}
