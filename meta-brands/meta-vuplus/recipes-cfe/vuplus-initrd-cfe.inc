SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "vuplus"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r2"
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://archive.vuplus.com/download/kernel/vmlinuz-initrd_${MACHINE}_${SRCDATE}.tar.gz"

S = "${WORKDIR}"
