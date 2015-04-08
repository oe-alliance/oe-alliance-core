SUMMARY = "Ceryon LCD Splash Files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "ceryon"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit deploy

PR = "r0"

S = "${WORKDIR}"

SRC_URI = "file://gbultraue/lcdwaitkey.bin \
           file://gbultraue/lcdwarning.bin \
           file://gbultraue/warning.bin \
"


ALLOW_EMPTY_${PN} = "1"

do_deploy() {
    install -m 0644 ${MACHINE}/lcdwaitkey.bin ${DEPLOYDIR}/lcdwaitkey.bin
    install -m 0644 ${MACHINE}/lcdwarning.bin ${DEPLOYDIR}/lcdwarning.bin
    install -m 0644 ${MACHINE}/warning.bin ${DEPLOYDIR}/warning.bin
}

addtask deploy before do_build after do_install
