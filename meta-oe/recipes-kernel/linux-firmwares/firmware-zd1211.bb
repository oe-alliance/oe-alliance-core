require linux-firmware.inc

SUMMARY = "Firmware for ZD1211"

SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/zd1211
    install -m 0644 zd1211/* ${D}/${nonarch_base_libdir}/firmware/zd1211/
}
