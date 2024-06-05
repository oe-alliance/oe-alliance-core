SUMMARY = "Ceryon LCD Splash Files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "ceryon"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit deploy

PR = "r3"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

SRC_URI = "file://lcdwaitkey220.bin \
           file://lcdwarning220.bin \
"

ALLOW_EMPTY:${PN} = "1"

do_deploy() {
    install -m 0644 ${S}/lcdwaitkey220.bin ${DEPLOYDIR}/lcdwaitkey220.bin
    install -m 0644 ${S}/lcdwarning220.bin ${DEPLOYDIR}/lcdwarning220.bin
}

addtask deploy before do_package after do_install
