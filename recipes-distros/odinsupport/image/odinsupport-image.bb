DESCRIPTION = "odin-support Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "odin-support team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "odinsupport-base"

export IMAGE_BASENAME = "odinsupport-image"
IMAGE_LINGUAS = ""

inherit image
