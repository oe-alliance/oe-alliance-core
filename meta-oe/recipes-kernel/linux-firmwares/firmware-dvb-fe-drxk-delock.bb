LICENSE = "CLOSED"

SUMMARY = "link firmware for EM2874B used in DeLock61959"
PACKAGE_ARCH = "all"

RDEPENDS_${PN} = "firmware-dvb-fe-drxk_a3"

S = "${WORKDIR}"

do_install() {
    ln -s ${D}/lib/firmware/drxk_a3.mc ${D}/lib/firmware/dvb-demod-drxk-01.fw
}

PACKAGES = "${PN}"
ALLOW_EMPTY_${PN} = "1"
