SUMMARY = "Platform Util drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc

PR="${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    http://archive.vuplus.com/download/build_support/vuplus/platform-util-${MACHINE}-${PV}-${PR}.tar.gz \
    file://${INITSCRIPT_NAME}.sysvinit \
"

S="${WORKDIR}/platform-util-${MACHINE}"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/dvb_server ${D}/usr/bin/dvb_server
    install -m 0755 ${S}/init_client ${D}/usr/bin/init_client
    install -m 0755 ${S}/config ${D}/usr/bin/config
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME}.sysvinit ${D}/etc/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}

FILES_${PN}="/"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 65 S . stop 90 0 ."
INITSCRIPT_NAME = "vuplus-platform-util"
