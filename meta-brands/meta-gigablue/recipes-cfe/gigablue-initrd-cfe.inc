SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Gigablue"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE}"

require conf/license/license-gplv2.inc

PR = "r2"
ALLOW_EMPTY_${PN} = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/gpt.bin ${D}/usr/share/gpt.bin
}

FILES_${PN} = "/usr/share"

inherit deploy
do_deploy() {
    if [ -e boot.bin  ]; then
    install -m 0644 boot.bin  ${DEPLOYDIR}/${MACHINE}_boot.bin
    fi
    if [ -e gpt.bin  ]; then
    install -m 0644 gpt.bin  ${DEPLOYDIR}/${MACHINE}_gpt.bin
    fi
    if [ -e rescue.bin  ]; then
    install -m 0644 rescue.bin  ${DEPLOYDIR}/${MACHINE}_rescue.bin
    fi
}

SRC_URI = "http://source.mynonpublic.com/gigablue/initrd/initrd_${MACHINE}_${SRCDATE}.zip"

S = "${WORKDIR}"

addtask deploy before do_build after do_install
