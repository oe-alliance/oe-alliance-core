SUMMARY = "player daemon helper"
MAINTAINER = "clap"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://clap-libreader-cc1.sh"

INITSCRIPT_NAME = "clap-libreader"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/clap-libreader-cc1.sh ${D}/etc/init.d/clap-libreader
}
