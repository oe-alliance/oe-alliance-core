SUMMARY = "Init script for Bluetooth Manager"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "proprietary"
require conf/license/license-gplv2.inc

inherit update-rc.d
INITSCRIPT_NAME = "BTInit.sh"
INITSCRIPT_PARAMS = "defaults 60 "

PR = "r0"

SRC_URI  = "file://${INITSCRIPT_NAME}"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/${INITSCRIPT_NAME} ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

do_package_qa() {
}

FILES:${PN}  = "${sysconfdir}"
