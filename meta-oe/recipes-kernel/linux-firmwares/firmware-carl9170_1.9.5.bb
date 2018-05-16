SUMMARY = "Firmware for CARL9170"
LICENCE = "closed"

require linux-firmware.inc

SRCREV = "c8157f24773d71bef6b45811a2d4b40eafa08f9c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ar9170-3.fw ${D}${nonarch_base_libdir}/firmware/carl9170-1.fw
}
