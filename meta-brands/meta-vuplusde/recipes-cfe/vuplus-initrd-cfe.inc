SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "vuplus.de"
LICENSE = "LicenseRef-proprietary"
PACKAGE_ARCH := "${MACHINE}"

require conf/license/license-gplv2.inc

PR = "r4"
ALLOW_EMPTY:${PN} = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${S}/initrd.bin ${D}/usr/share/initrd.bin
    install -m 0644 ${S}/rescue.bin ${D}/usr/share/rescue.bin
    if [ -e ${S}/boot.bin  ]; then
    install -m 0644 ${S}/boot.bin  ${D}/usr/share/boot.bin
    fi
}

FILES:${PN} = "/usr/share"

inherit deploy
do_deploy() {
    if [ -e boot.bin  ]; then
    install -m 0644 boot.bin  ${DEPLOYDIR}/${MACHINE}_boot.bin
    fi
    if [ -e initrd.bin  ]; then
    install -m 0644 initrd.bin  ${DEPLOYDIR}/${MACHINE}_initrd.bin
    fi
    if [ -e rescue.bin  ]; then
    install -m 0644 rescue.bin  ${DEPLOYDIR}/${MACHINE}_rescue.bin
    fi
}

SRC_URI = "https://source.mynonpublic.com/vuplus.de/initrd/initrd_${MACHINE}_${SRCDATE}.zip"

S = "${UNPACKDIR}"

addtask deploy before do_package after do_install
