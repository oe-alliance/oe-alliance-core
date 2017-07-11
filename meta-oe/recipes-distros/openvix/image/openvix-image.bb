SUMMARY = "OpenViX Image"
MAINTAINER = "OpenViX"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"

PV = "${IMAGE_VERSION}"
PR = "r${PR_NUM}"

IMAGE_INSTALL = "openvix-base"

export IMAGE_BASENAME = "openvix-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
