SUMMARY = "kernel modules load helper"
MAINTAINER = "uclan"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://uclan-loadmodules-hisi3798mv300.sh"

INITSCRIPT_NAME = "uclan-loadmodules"
INITSCRIPT_PARAMS = "start 04 S ."

inherit update-rc.d

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/uclan-loadmodules-hisi3798mv300.sh ${D}/etc/init.d/uclan-loadmodules
}
