SUMMARY = "OpenBh Image"
MAINTAINER = "OpenBh"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

REPRODUCIBLE_TIMESTAMP_ROOTFS = "${@int(time.time()-10000)}"

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"

PV = "${IMAGE_VERSION}"
PR = "r${PR_NUM}"

image_combine_spdx() {
}

SSTATE_ALLOW_OVERLAP_FILES += "${DEPLOY_DIR_IMAGE}"

IMAGE_INSTALL = "openbh-base"

# Some additional comfort on the shell: Pre-install nano on boxes with 128 MB or more:
#IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "nano", d)}"

# ... plus mc and helpers on 256 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "mc mc-fish mc-helpers", d)}"

export IMAGE_BASENAME = "openbh-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

INHIBIT_DEFAULT_DEPS = "1"

inherit image

do_package_index[nostamp] = "1"
do_package_index[depends] += "${PACKAGEINDEXDEPS}"

python do_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}
addtask do_package_index after do_rootfs before do_image_complete
