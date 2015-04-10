SUMMARY = "Ceryon LCD Splash Files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "ceryon"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit deploy

PR = "r2"

S = "${WORKDIR}"

SRC_URI = "file://lcdwaitkey.bin \
           file://lcdwarning.bin \
           file://lcdcomplete.bin \
"


ALLOW_EMPTY_${PN} = "1"

do_deploy() {
    install -m 0644 ${WORKDIR}/lcdwaitkey.bin ${DEPLOYDIR}/lcdwaitkey.bin
    install -m 0644 ${WORKDIR}/lcdwarning.bin ${DEPLOYDIR}/lcdwarning.bin
    install -m 0644 ${WORKDIR}/lcdcomplete.bin ${DEPLOYDIR}/lcdcomplete.bin
}

addtask deploy before do_build after do_install
