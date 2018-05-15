require linux-firmware.inc

SUMMARY = "Firmware for as102_data2_st"

SRCREV = "61a66a03697b3cdc04e244688fa716aa13b3bf12"

PR = "r1"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 as102_data2_st.hex ${D}${nonarch_base_libdir}/firmware
}
