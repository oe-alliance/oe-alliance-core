SUMMARY = "kernel modules load helper"
MAINTAINER = "uclan"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://uclan-loadmodules-ustym4kpro.sh"

INITSCRIPT_NAME = "uclan-loadmodules"
INITSCRIPT_PARAMS = "start 01 S ."

inherit update-rc.d

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/uclan-loadmodules-ustym4kpro.sh ${D}/etc/init.d/uclan-loadmodules
}
