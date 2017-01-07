SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "DAGS"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

PR = "r0"
ALLOW_EMPTY_${PN} = "1"

inherit deploy
do_deploy() {
    if [ -e initrd_run.bin  ]; then
    install -m 0644 vmlinuz-initrd-7439b0  ${DEPLOYDIR}/initrd_run.bin
    fi
}

SRC_URI = "http://en3homeftp.net/pub/down/vmlinuz-initrd_${MACHINEBUILD}_${SRCDATE}.tar.gz"

S = "${WORKDIR}"

addtask deploy before do_build after do_install
