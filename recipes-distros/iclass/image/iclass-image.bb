DESCRIPTION = "iclass Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "iclass team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "iclass-base"

export IMAGE_BASENAME = "iclass-image"
IMAGE_LINGUAS = ""

inherit image
