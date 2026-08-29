SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "vuplus"
PACKAGE_ARCH := "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

PR = "r3"
ALLOW_EMPTY:${PN} = "1"

SRC_URI = "https://source.mynonpublic.com/vuplus/release/kernel/vmlinuz-initrd_${MACHINEBUILD}_${SRCDATE}.tar.gz"

S = "${UNPACKDIR}"
