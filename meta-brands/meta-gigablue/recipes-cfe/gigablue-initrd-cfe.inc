SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Gigablue"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

PR = "r1"
ALLOW_EMPTY_${PN} = "1"

inherit deploy
do_deploy() {
    if [ -e rescue.bin  ]; then
    install -m 0644 rescue.bin  ${DEPLOYDIR}/${MACHINEBUILD}_rescue.bin
    fi
}

SRC_URI = "http://archiv.openmips.com/beta/initrd_${MACHINEBUILD}_${SRCDATE}.zip"

S = "${WORKDIR}"

addtask deploy before do_build after do_install
