DESCRIPTION = "Xtrend-Alliance Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Xtrend-Alliance team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "xtrendalliance-base"

export IMAGE_BASENAME = "xtrendalliance-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
