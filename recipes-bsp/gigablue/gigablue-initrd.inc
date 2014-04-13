DESCRIPTION = "initrd usb flasher support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "gigablue"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r2"
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://archiv.openmips.com/gigablue-initrd-${MACHINE}-${SRCDATE}.tgz"

S = "${WORKDIR}"

inherit deploy
do_deploy() {
    if [ -e initrd.bin ]; then
    install -m 0644 initrd.bin ${DEPLOYDIR}/initrd.bin
    fi
}

addtask deploy before do_build after do_install
