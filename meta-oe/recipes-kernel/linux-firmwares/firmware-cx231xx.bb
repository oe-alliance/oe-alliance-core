require linux-firmware.inc

SUMMARY = "Firmware for cx231xx"

SRCREV = "6dda26f406b6eb85c5a364a85a83a73f217395ad"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 v4l-cx231xx-avcore-01.fw ${D}${nonarch_base_libdir}/firmware
}
