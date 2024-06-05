SUMMARY = "kernel modules load helper"
MAINTAINER = "octagon"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://dags-loadmodules-hisi3798mv200.sh"

INITSCRIPT_NAME = "dags-loadmodules"
INITSCRIPT_PARAMS = "start 01 S ."

inherit update-rc.d

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${S}/dags-loadmodules-hisi3798mv200.sh ${D}/etc/init.d/dags-loadmodules
}
