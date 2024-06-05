SUMMARY = "Platform Util drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE}"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r6"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

SRC_URI = " \
    https://source.mynonpublic.com/gigablue/drivers/platform-util-${MACHINE}-${KV}-${SRCDATE}.zip \
    file://${INITSCRIPT_NAME}.sysvinit \
"

S="${WORKDIR}/platform"

do_install() {
    install -d ${D}/home/root/platform
    install -m 0755 ${UNPACKDIR}/* ${D}/home/root/platform
    install -d ${D}/etc/init.d
    install -m 0755 ${S}/${INITSCRIPT_NAME}.sysvinit ${D}/etc/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}
do_populate_sysroot() {
}

FILES:${PN}="/home/root/platform /etc/init.d"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 65 S . stop 90 0 ."
INITSCRIPT_NAME = "gigablue-platform-util"

INSANE_SKIP:${PN} += "already-stripped"
