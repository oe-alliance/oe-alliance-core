SUMMARY = "Open Vision Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

REPRODUCIBLE_TIMESTAMP_ROOTFS = "${@time.strftime('%s')}"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"

do_rootfs[deptask] = "do_rm_work"

SSTATE_ALLOW_OVERLAP_FILES += "${DEPLOY_DIR_IMAGE}"

IMAGE_INSTALL = "openvision-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "no-cl-svr", "", "packagegroup-base-nfs", d)} \
    "

export IMAGE_BASENAME = "openvision-image"

# Prevent locales-archive creation to save some space
IMAGE_LOCALES_ARCHIVE = ""
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
