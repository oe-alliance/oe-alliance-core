SUMMARY = "Firmware for mt7601u"
LICENSE = "CLOSED"
require conf/license/license-close.inc
inherit allarch
SRC_URI = "file://firmware-mt7601u.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

PACKAGES = "${PN}"
FILES:${PN} += "${nonarch_base_libdir}/firmware"


do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/mt7601u.bin ${D}${nonarch_base_libdir}/firmware/mt7601u.bin
}
