SUMMARY = "kernel modules load helper"
MAINTAINER = "AMIKO"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://amiko-loadmodules-viper4k.sh"

INITSCRIPT_NAME = "amiko-loadmodules"
INITSCRIPT_PARAMS = "start 01 S ."

inherit update-rc.d

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${S}/amiko-loadmodules-viper4k.sh ${D}/etc/init.d/amiko-loadmodules
}
