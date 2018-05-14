LICENSE = "CLOSED"
require conf/license/license-close.inc

SUMMARY = "Firmware for ds3xxx dvb frontend"
inherit allarch

SRC_URI = "file://fw-ds3xxx.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0755 dvb-fe-ds300x.fw ${D}${base_libdir}/firmware/dvb-fe-ds300x.fw
    install -m 0755 dvb-fe-ds3103.fw ${D}${base_libdir}/firmware/dvb-fe-ds3103.fw
}

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"
