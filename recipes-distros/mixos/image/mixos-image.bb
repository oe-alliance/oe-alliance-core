DESCRIPTION = "MixOS Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "MixOS Support"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "mixos-base"

export IMAGE_BASENAME = "mixos-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
