LICENSE = "CLOSED"
SRC_URI = "file://mn8847x.zip"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${nonarch_base_libdir}/firmware"

inherit allarch

SUMMARY = "Firmware for mn8847x"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware
    install -m 0644 mn88472.fw ${D}${nonarch_base_libdir}/firmware/mn88472.fw
    install -m 0644 dvb-demod-mn88472-02.fw ${D}${nonarch_base_libdir}/firmware/dvb-demod-mn88472-02.fw
    install -m 0644 mn88473.fw ${D}${nonarch_base_libdir}/firmware/mn88473.fw
    install -m 0644 mn88473.fw ${D}${nonarch_base_libdir}/firmware/dvb-demod-mn88473-01.fw
}
