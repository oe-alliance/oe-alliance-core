LICENSE = "CLOSED"
require conf/license/license-close.inc

SUMMARY = "Firmware for drxk dvb-c/dvb-t dvb frontend"
inherit allarch

SRC_URI = "file://drxk_a3.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0755 drxk_a3.mc ${D}${nonarch_base_libdir}/firmware/drxk_a3.mc
}

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"
