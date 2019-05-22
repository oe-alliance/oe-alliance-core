LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = " \
    file://rtl8188fufw.zip \
"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for Realtek 8188fu"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8188fufw.bin ${D}${nonarch_base_libdir}/firmware/rtlwifi
}
