require linux-firmware.inc

SUMMARY = "Firmware for CARL9170"

SRCREV = "3aa7bab757fdb0e38c484e73d73c6d34a770c1ba"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ar9170-3.fw ${D}${nonarch_base_libdir}/firmware/carl9170-1.fw
}
