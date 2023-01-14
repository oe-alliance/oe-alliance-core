SUMMARY = "Firmware files for uwe5621"
LICENSE = "CLOSED"
require conf/license/license-close.inc
inherit allarch

SRC_URI = "file://uwe5621.zip"

S = "${WORKDIR}"

FILES_${PN} += "${nonarch_base_libdir}/firmware"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/uwewifi
    install -m 0644 wcnmodem.bin ${D}${nonarch_base_libdir}/firmware
    install -m 0644 *.ini ${D}${nonarch_base_libdir}/firmware/uwewifi
}
