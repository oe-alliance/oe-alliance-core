SUMMARY = "2nd bootloader support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "DAGS"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINEBUILD}"

require conf/license/license-gplv2.inc

PR = "r0"
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://en3homeftp.net/pub/down/partition_ramdisk_for_${MACHINEBUILD}.tar.gz"

S = "${WORKDIR}"
