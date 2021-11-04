SUMMARY = "Availink demodulator firmware"
HOMEPAGE = "http://www.availink.com/"
LICENSE = "CLOSED"

SRC_URI = "git://github.com/edision-open/linux-firmware.git;branch=master;protocol=https"

SRCREV = "824e8de5b7a048eaf49221f7ba788dbb79123715"

S = "${WORKDIR}/git"

inherit allarch

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/
    install -d ${D}${nonarch_base_libdir}/firmware/availink/
    install -m 0644 availink/dvb-fe-avl62x1.fw ${D}${nonarch_base_libdir}/firmware/availink/
}

FILES:${PN} += "${nonarch_base_libdir}/firmware"
