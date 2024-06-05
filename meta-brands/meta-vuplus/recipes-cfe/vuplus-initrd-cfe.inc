SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "vuplus"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r3"
ALLOW_EMPTY:${PN} = "1"

SRC_URI = "https://source.mynonpublic.com/vuplus/release/kernel/vmlinuz-initrd_${MACHINE}_${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"
