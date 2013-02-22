DESCRIPTION = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "vuplus"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r6"
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://archive.vuplus.com/download/kernel/vmlinuz-initrd_${MACHINE}_20130212.tar.gz"

S = "${WORKDIR}"

inherit deploy
do_deploy() {
	if [ -e vmlinuz-initrd-7346b0 ]; then
	install -m 0644 vmlinuz-initrd-7346b0 ${DEPLOYDIR}/initrd_cfe_auto.bin
	fi
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "3b45489e7902cbf98e9abdddea14567a"
SRC_URI[sha256sum] = "e7a7e747dcd7240c5d36c2235d11b2d0e703ed55be1120d6109220478d23fb09"
