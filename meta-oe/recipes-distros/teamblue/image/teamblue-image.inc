SUMMARY = "teamBlue Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "teamBlue team"

require conf/license/license-gplv2.inc

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"
PV = "${IMAGE_VERSION}"
PR = "r${PR_NUM}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

image_combine_spdx() {
}

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"
REPRODUCIBLE_TIMESTAMP_ROOTFS = "${@int(time.time()-10000)}"
do_rootfs[deptask] = "do_rm_work"

export IMAGE_BASENAME = "teamblue-image"
SSTATE_ALLOW_OVERLAP_FILES += "${DEPLOY_DIR_IMAGE}"

IMAGE_INSTALL = "teamblue-base"

# Some additional comfort on the shell: Pre-install nano on boxes with 128 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "iproute2", d)}"

# 64 or 128MB of flash: No language files, above: German
#IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "de-de", d)}"

# Add more languages for 512 or more MB of flash:
#IMAGE_LINGUAS += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "", "es-es it-it nl-nl pt-pt", d)}"

# Prevent locales-archive creation to save some space
IMAGE_LOCALES_ARCHIVE = ""


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
