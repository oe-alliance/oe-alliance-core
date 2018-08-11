SUMMARY = "S3 cold boot"
MAINTAINER = "ini Team"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r6"

SRC_URI = "file://coldboot file://coldboot.sh"

inherit update-rc.d
INITSCRIPT_NAME = "coldboot"
INITSCRIPT_PARAMS = "start 30 0 ."

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/init.d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/coldboot.sh ${D}/etc/init.d/coldboot
    install -m 0755 ${WORKDIR}/coldboot ${D}/usr/bin/coldboot
}
