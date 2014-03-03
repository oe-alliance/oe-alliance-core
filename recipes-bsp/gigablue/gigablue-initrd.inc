DESCRIPTION = "initrd usb flasher support"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "gigablue"
LICENSE = "proprietary"
PACKAGE_ARCH := "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR = "r1"
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://archiv.openmips.com/initrd-${MACHINE}-${SRCDATE}.tgz"

S = "${WORKDIR}"
