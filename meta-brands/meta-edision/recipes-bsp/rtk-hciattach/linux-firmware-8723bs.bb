SUMMARY = "Realtek 8723B firmware"
HOMEPAGE = "https://www.realtek.com/"
LICENSE = "CLOSED"

SRC_URI = "git://github.com/edision-open/linux-firmware.git;branch=master;protocol=https"

SRCREV = "5ad65c3de04150106e69b5d6517c838226618798"

S = "${WORKDIR}/git"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -d ${D}${nonarch_base_libdir}/firmware/rtlbt/
    install -m 0644 rtlbt/rtl8723b_fw ${D}${nonarch_base_libdir}/firmware/rtlbt/
}

FILES:${PN} += "${nonarch_base_libdir}/firmware"
