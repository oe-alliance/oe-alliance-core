SUMMARY = "vuplus-hdmi-in-helper"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r4"

SRC_URI = " \
    file://update_systemconfig_arm \
    file://update_systemconfig.sh \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/update_systemconfig_arm ${D}${bindir}/update_systemconfig
    install -m 0755 ${WORKDIR}/update_systemconfig.sh ${D}${sysconfdir}/init.d/
}

inherit update-rc.d

INITSCRIPT_NAME = "update_systemconfig.sh"
INITSCRIPT_PARAMS = "start 90 3 ."

PACKAGE_ARCH = "${MACHINE_ARCH}"
