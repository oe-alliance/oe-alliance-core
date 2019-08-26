SUMMARY = "player daemon helper"
MAINTAINER = "AMIKO"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://amiko-libreader-viper4k.sh"

INITSCRIPT_NAME = "amiko-libreader"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

S = "${WORKDIR}"

do_compile() {
}

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/amiko-libreader-viper4k.sh ${D}${sysconfdir}/init.d/amiko-libreader
}
