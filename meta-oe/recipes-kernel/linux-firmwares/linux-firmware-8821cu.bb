SUMMARY = "Realtek 8821CU firmware"
HOMEPAGE = "https://www.realtek.com/"
LICENSE = "CLOSED"
require conf/license/license-close.inc

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/atvcaptain/linux-firmware.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 8821CU/rtl8821cu_config ${D}${nonarch_base_libdir}/firmware/
    install -m 0644 8821CU/rtl8821cu_fw ${D}${nonarch_base_libdir}/firmware/
}

FILES_${PN} += "${nonarch_base_libdir}/firmware"
