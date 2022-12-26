require linux-firmware.inc

SUMMARY = "Firmware for cx231xx"

SRCREV = "bf9f8648fdf1d1d63db471554781f897d219bd62"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 v4l-cx231xx-avcore-01.fw ${D}${nonarch_base_libdir}/firmware
}
