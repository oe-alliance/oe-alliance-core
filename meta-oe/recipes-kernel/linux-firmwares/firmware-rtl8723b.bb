LICENSE = "CLOSED"
PR = "r02"
SRC_URI = "file://rtl8723b_fw.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

PACKAGE_ARCH = "all"

SUMMARY = "Firmware for rtl8723b"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -d ${D}${base_libdir}/firmware/rtl_bt
    install -m 0644 rtl8723b_fw.bin ${D}${base_libdir}/firmware/rtl_bt
}
