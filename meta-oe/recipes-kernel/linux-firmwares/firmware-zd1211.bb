require linux-firmware.inc

SUMMARY = "Firmware for ZD1211"

SRCREV = "bf9f8648fdf1d1d63db471554781f897d219bd62"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/zd1211
    install -m 0644 zd1211/* ${D}/${nonarch_base_libdir}/firmware/zd1211/
}
