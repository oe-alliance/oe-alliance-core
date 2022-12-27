require linux-firmware.inc



SRCREV = "46c66487a85cd05a4acbd5eb4828f72783d1be4c"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 rt3070.bin ${D}${nonarch_base_libdir}/firmware/
}

RDEPENDS:${PN} = "firmware-rt2870"