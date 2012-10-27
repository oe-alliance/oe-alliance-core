DESCRIPTION = "OpenViX Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "ViX team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "openvix-base"

export IMAGE_BASENAME = "openxix-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
