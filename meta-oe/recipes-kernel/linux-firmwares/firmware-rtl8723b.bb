LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://rtl8723b_fw.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for rtl8723b"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -d ${D}${base_libdir}/firmware/rtl_bt
    install -m 0644 rtl8723b_fw.bin ${D}${base_libdir}/firmware/rtl_bt
}
