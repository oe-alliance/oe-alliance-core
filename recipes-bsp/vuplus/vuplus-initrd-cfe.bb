DESCRIPTION = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "openViX"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r2"
ALLOW_EMPTY_${PN} = "1"

SRC_URI_append = "http://archive.vuplus.com/download/kernel/vmlinuz-initrd_${MACHINE}_20130212.tar.gz"

S = "${WORKDIR}/"

do_install() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/vmlinuz-initrd-7346b0 ${DEPLOY_DIR_IMAGE}/initrd_cfe_auto.bin
}
