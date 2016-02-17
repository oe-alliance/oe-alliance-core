SUMMARY = "S3 set eth0 wol"
MAINTAINER = "GigaBlue"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r3"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://ethwol.sh"

S = "${WORKDIR}"

inherit update-rc.d
INITSCRIPT_NAME = "ethwol"
INITSCRIPT_PARAMS = "stop 32 0 ."

do_install() {
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/ethwol.sh ${D}/etc/init.d/ethwol
}
