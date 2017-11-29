SUMMARY = "kernel modules load helper"
MAINTAINER = "dinobot"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://dinobot-loadmodules-u5.sh"

INITSCRIPT_NAME = "dinobot-loadmodules"
INITSCRIPT_PARAMS = "start 01 S ."

inherit update-rc.d

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/dinobot-loadmodules-u5.sh ${D}/etc/init.d/dinobot-loadmodules
}
