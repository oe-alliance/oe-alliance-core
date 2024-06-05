SUMMARY = "kernel modules load helper"
MAINTAINER = "anadol"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://anadol-loadmodules-hisi3798mv200advca.sh"

INITSCRIPT_NAME = "anadol-loadmodules"
INITSCRIPT_PARAMS = "start 01 S ."

inherit update-rc.d

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${S}/anadol-loadmodules-hisi3798mv200advca.sh ${D}/etc/init.d/anadol-loadmodules
}
