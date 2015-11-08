SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "${SRCDATE}.${SRCDATE_PR}"

SRC_URI = "http://archive.vuplus.com/download/build_support/vuplus/vuplus-dvb-proxy-${MACHINE}-${PV}-${PR}.tar.gz "

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    for f in *.ko; do
        install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}/extra/$f;
    done
}

