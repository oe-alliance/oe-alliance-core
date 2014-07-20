LICENSE = "CLOSED"

SUMMARY = "Firmware for EM2874B used in DeLock61959"
PACKAGE_ARCH = "all"

SRC_URI = "file://dvb-demod-drxk-01.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/lib/firmware
    install -m 0755 dvb-demod-drxk-01.fw ${D}/lib/firmware/dvb-demod-drxk-01.fw
}

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware"
