DESCRIPTION = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "vuplus"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r1"
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://archive.vuplus.com/download/kernel/vmlinuz-initrd_${MACHINE}_${SRCDATE}.tar.gz"

S = "${WORKDIR}"

inherit deploy
do_deploy() {
	if [ -e vmlinuz-initrd-7346b0 ]; then
	install -m 0644 vmlinuz-initrd-7346b0 ${DEPLOYDIR}/initrd_cfe_auto.bin
	fi
}

addtask deploy before do_build after do_install
