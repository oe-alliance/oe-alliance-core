SUMMARY = "player daemon helper"
MAINTAINER = "beyonwiz"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r1"

SRC_URI = "file://beyonwiz-libreader-beyonwizv2.sh"

INITSCRIPT_NAME = "beyonwiz-libreader"
INITSCRIPT_PARAMS = "start 05 S ."

inherit update-rc.d

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_compile() {
}

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${S}/beyonwiz-libreader-beyonwizv2.sh ${D}/etc/init.d/beyonwiz-libreader
}
