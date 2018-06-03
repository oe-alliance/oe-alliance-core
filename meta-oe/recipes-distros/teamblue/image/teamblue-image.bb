SUMMARY = "teamBlue Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "teamBlue team"

require conf/license/license-gplv2.inc

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"
PV = "${IMAGE_VERSION}"
PR = "r${PR_NUM}"
PACKAGE_ARCH = "${MACHINEBUILD}"

do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "teamblue-base"

export IMAGE_BASENAME = "teamblue-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
