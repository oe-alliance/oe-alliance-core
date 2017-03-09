SUMMARY = "image update support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "octagon"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r0"
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://source.mynonpublic.com/octagon/vmlinuz-initrd-${MACHINE}-${SRCDATE}.zip"

S = "${WORKDIR}"

addtask deploy before do_build after do_install
