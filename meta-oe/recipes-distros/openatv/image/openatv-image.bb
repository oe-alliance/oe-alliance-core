SUMMARY = "OpenATV Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openATV team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "openatv-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "enigma2-plugin-settings-defaultsat", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "", \
    " \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    packagegroup-base-nfs \
    ", d)} \
    "

export IMAGE_BASENAME = "openatv-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image
