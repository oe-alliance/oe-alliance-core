SUMMARY = "image update support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "octagon"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r0"
ALLOW_EMPTY:${PN} = "1"

SRC_URI = "https://source.mynonpublic.com/octagon/vmlinuz-initrd-${MACHINE}-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

addtask deploy before do_package after do_install
