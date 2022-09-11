SUMMARY = "Platform Util drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"
require conf/license/license-close.inc

PR="${SRCDATE}"

SRC_URI = " \
    https://source.mynonpublic.com/dags/platform-util-${MACHINE}.zip \
    file://${INITSCRIPT_NAME}.sysvinit \
"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/* ${D}${bindir}
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME}.sysvinit ${D}/etc/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}
do_populate_sysroot() {
}

FILES_${PN}="/"

inherit update-rc.d

INITSCRIPT_PARAMS = "start 65 S . stop 90 0 ."
INITSCRIPT_NAME = "dags-platform-util"

INSANE_SKIP_${PN} += "already-stripped"
