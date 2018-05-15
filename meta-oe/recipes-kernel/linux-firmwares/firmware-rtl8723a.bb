LICENSE = "CLOSED"
require conf/license/license-close.inc
PR = "r0"
SRC_URI = " \
    file://rtl8723a_fw.zip \
    file://rtl8723aufw_B_NoBT.zip \
"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for rtl8723a"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/rtl_bt
    install -m 0644 rtl8723a_fw.bin ${D}${nonarch_base_libdir}/firmware/rtl_bt
    install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8723aufw_B_NoBT.bin ${D}${nonarch_base_libdir}/firmware/rtlwifi
}
