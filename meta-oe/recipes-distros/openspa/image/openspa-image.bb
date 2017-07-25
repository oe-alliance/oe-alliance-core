SUMMARY = "openSPA Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openSPA team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "openspa-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "no-cl-svr", "", \
    " \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-nfs \
    ", d)} \
    "

export IMAGE_BASENAME = "openspa-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
