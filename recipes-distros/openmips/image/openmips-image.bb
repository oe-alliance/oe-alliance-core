DESCRIPTION = "OpenMips Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openmips team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r5${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "openmips-base"


export IMAGE_BASENAME = "openmips-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
