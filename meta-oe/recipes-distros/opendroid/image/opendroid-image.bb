SUMMARY = "openDroid Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "OpenDroid Team"

require conf/license/license-gplv2.inc

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"
PV = "${IMAGE_VERSION}"
PR = "r${PR_NUM}"

IMAGE_INSTALL = "opendroid-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "", \
    " \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    packagegroup-base-nfs \
    ", d)} \
    "

export IMAGE_BASENAME = "opendroid-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
