LICENSE = "CLOSED"
require conf/license/license-close.inc
SRC_URI = " \
    file://rtl8192cufw_TMSC.zip \
"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for rtl8192cufw_TMSC"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${nonarch_base_libdir}/firmware/rtlwifi
    install -m 0644 ${UNPACKDIR}/rtl8192cufw_TMSC.bin ${D}${nonarch_base_libdir}/firmware/rtlwifi
}
