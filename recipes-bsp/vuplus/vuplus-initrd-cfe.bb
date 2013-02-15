DESCRIPTION = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "vuplus"
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

SRC_URI[md5sum] = "3b45489e7902cbf98e9abdddea14567a"
SRC_URI[sha256sum] = "e7a7e747dcd7240c5d36c2235d11b2d0e703ed55be1120d6109220478d23fb09"
