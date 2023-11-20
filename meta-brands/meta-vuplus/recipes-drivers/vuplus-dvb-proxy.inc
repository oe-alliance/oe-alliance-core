SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "${SRCDATE}.${SRCDATE_PR}"

#SRC_URI = "http://code.vuplus.com/download/release/vuplus-dvb-proxy/vuplus-dvb-proxy-${MACHINE}-${PV}-${PR}.tar.gz "
SRC_URI = "https://source.mynonpublic.com/vuplus/vuplus-dvb-proxy-${MACHINE}-${PV}-${PR}.tar.gz "


S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_compile() {
}
do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    for f in *.ko; do
        install -m 0644 ${WORKDIR}/$f ${D}/lib/modules/${KV}/extra/$f;
    done
}

FILES:${PN} += "/lib/modules/${KV}/extra"
