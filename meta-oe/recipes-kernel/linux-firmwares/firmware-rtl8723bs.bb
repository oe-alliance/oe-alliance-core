LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://firmware_rtlwifi.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

PACKAGE_ARCH = "all"

SUMMARY = "Firmware for rtl8723bs"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -d ${D}${base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8723bs_ap_wowlan.bin ${D}${base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8723bs_bt.bin ${D}${base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8723bs_nic.bin ${D}${base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8723bs_wowlan.bin ${D}${base_libdir}/firmware/rtlwifi
}
