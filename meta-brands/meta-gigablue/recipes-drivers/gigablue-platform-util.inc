SUMMARY = "Platform Util drivers for ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINEBUILD}"
require conf/license/license-close.inc

PV = "${KV}+${SRCDATE}"
PR = "r1"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

SRC_URI = " \
    http://archiv.openmips.com/beta/platform-util-${MACHINEBUILD}-${SRCDATE}.zip \
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
do_populate_sysroot() {
}

FILES_${PN}="/home/root /etc/init.d"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 65 S . stop 90 0 ."
INITSCRIPT_NAME = "gigablue-platform-util"

INSANE_SKIP_${PN} += "already-stripped"
