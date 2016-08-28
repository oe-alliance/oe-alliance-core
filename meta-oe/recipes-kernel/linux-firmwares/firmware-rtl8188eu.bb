LICENSE = "CLOSED"
require conf/license/license-close.inc
PR = "r1"
SRC_URI = "file://rtl8188eufw.bin"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"

PACKAGE_ARCH = "all"

SUMMARY = "Firmware for RTL8188EU"

do_install() {
    install -d ${D}${base_libdir}/firmware/rtlwifi
    install -m 0644 rtl8188eufw.bin ${D}/${base_libdir}/firmware/rtlwifi/
}
