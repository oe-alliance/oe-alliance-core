# OE-Alliance Image Base - common image boilerplate
# All image recipes should require this for consistent behavior

SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

REPRODUCIBLE_TIMESTAMP_ROOTFS = "${@int(time.time()-10000)}"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"

# Required for pseudo to set file ownership in rootfs
PSEUDO_INCLUDE_PATHS .= ",${TMPDIR}/rootfs"

do_rootfs[deptask] = "do_rm_work"

SSTATE_ALLOW_OVERLAP_FILES += "${DEPLOY_DIR_IMAGE}"

IMAGE_LOCALES_ARCHIVE = ""
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management allow-empty-password empty-root-password allow-root-login"

inherit image

do_package_index[nostamp] = "1"
do_package_index[depends] += "${PACKAGEINDEXDEPS}"

python do_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}
addtask do_package_index after do_rootfs before do_image_complete
