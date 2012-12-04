DESCRIPTION = "Bootlogo support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "openViX"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r1"
ALLOW_EMPTY_${PN} = "1"

SRC_URI_append_vuduo2 = " file://initrd_cfe_auto.bin"
SRC_URI_append_vusolo2 = " file://initrd_cfe_auto.bin"

S = "${WORKDIR}/"

do_install() {
	install -d ${DEPLOY_DIR_IMAGE}
	install -m 0755 ${S}/initrd_cfe_auto.bin ${DEPLOY_DIR_IMAGE}/initrd_cfe_auto.bin
}
