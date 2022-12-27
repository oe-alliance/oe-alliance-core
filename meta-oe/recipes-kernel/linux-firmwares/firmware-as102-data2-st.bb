require linux-firmware.inc

SUMMARY = "Firmware for as102_data2_st"

SRCREV = "3aa7bab757fdb0e38c484e73d73c6d34a770c1ba"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 as102_data2_st.hex ${D}${nonarch_base_libdir}/firmware
}
