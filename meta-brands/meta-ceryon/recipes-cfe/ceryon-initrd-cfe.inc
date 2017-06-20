SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Ceryon"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE}"

require conf/license/license-gplv2.inc

PR = "r1"
ALLOW_EMPTY_${PN} = "1"

inherit deploy
do_deploy() {
    if [ -e initrd_kernel.bin  ]; then
    install -m 0644 initrd_kernel.bin  ${DEPLOYDIR}/${MACHINE}_initrd_kernel.bin
    fi
}

SRC_URI = "http://source.mynonpublic.com/ceryon/ceryon-${MACHINE}-initrd-${SRCDATE}.zip"

S = "${WORKDIR}"

addtask deploy before do_build after do_install
