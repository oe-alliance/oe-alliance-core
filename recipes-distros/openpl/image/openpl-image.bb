DESCRIPTION = "openPL Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openPL"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "openpl-base"

export IMAGE_BASENAME = "openpl-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
