SUMMARY = "player daemon helper"
MAINTAINER = "octagon"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://octagon-libreader-hisi3716mv430.sh"

INITSCRIPT_NAME = "octagon-libreader"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${S}/octagon-libreader-hisi3716mv430.sh ${D}/etc/init.d/octagon-libreader
}
