DESCRIPTION = "OpenAAF Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openAAF team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "openaaf-base"

export IMAGE_BASENAME = "openaaf-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
