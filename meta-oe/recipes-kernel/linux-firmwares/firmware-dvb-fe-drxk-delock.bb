LICENSE = "CLOSED"
require conf/license/license-close.inc

SUMMARY = "link firmware for EM2874B used in DeLock61959"
inherit allarch

RDEPENDS_${PN} = "firmware-dvb-fe-drxk_a3"

S = "${WORKDIR}"

do_install() {
    ln -s ${D}/lib/firmware/drxk_a3.mc ${D}/lib/firmware/dvb-demod-drxk-01.fw
}

PACKAGES = "${PN}"
ALLOW_EMPTY_${PN} = "1"
