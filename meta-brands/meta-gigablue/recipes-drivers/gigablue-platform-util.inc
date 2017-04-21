SUMMARY = "Platform Util drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc

INHIBIT_PACKAGE_STRIP = "1"

SRC_URI = " \
    http://archiv.openmips.com/beta/platform-util-${MACHINE}-${SRCDATE}.zip \
    file://${INITSCRIPT_NAME}.sysvinit \
"

S="${WORKDIR}/platform"

do_install() {
    install -d ${D}/home/root
    install -m 0755 ${S}/* ${D}/home/root
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME}.sysvinit ${D}/etc/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}

FILES_${PN}="/"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 65 S . stop 90 0 ."
INITSCRIPT_NAME = "gigablue-platform-util"

INSANE_SKIP_${PN} += "already-stripped"
