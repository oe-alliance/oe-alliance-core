SUMMARY = "Firmware for mt7601u"
LICENSE = "CLOSED"
require conf/license/license-close.inc
inherit allarch
PR = "r00"
SRC_URI = "file://firmware-mt7601u.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"


do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 ${S}/mt7601u.bin ${D}${nonarch_base_libdir}/firmware/mt7601u.bin
}
