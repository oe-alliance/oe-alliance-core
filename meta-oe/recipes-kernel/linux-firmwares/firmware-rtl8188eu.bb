LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = "file://rtl8188eufw.bin"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for RTL8188EU"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8188eufw.bin ${D}/${nonarch_base_libdir}/firmware/rtlwifi/
}
