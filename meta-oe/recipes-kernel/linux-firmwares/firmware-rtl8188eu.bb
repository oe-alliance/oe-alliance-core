LICENSE = "CLOSED"
require conf/license/license-close.inc
PR = "r1"
SRC_URI = "file://rtl8188eufw.bin"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for RTL8188EU"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8188eufw.bin ${D}/${nonarch_base_libdir}/firmware/rtlwifi/
}
