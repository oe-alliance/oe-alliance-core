SUMMARY = "Availink demodulator firmware"
HOMEPAGE = "http://www.availink.com/"
LICENSE = "CLOSED"

SRC_URI = "git://github.com/availink/linux-firmware-availink.git;branch=master;protocol=https"

SRCREV = "f38a3a9b9906d192075984c7ac1100d51adf75a1"

S = "${WORKDIR}/git"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -d ${D}${nonarch_base_libdir}/firmware/availink/
    install -m 0644 availink/dvb-fe-avl62x1.fw ${D}${nonarch_base_libdir}/firmware/availink/
}

FILES:${PN} += "${nonarch_base_libdir}/firmware"
