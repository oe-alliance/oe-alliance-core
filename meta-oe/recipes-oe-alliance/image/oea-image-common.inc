# OE-Alliance Image Base - common image boilerplate
# All image recipes should require this for consistent behavior

SECTION = "base"
PRIORITY = "required"
LICENSE = "LicenseRef-proprietary"

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

# minilocale already provides every locale-base-* an image could ask for,
# so the IMAGE_LINGUAS-derived request is redundant. Since glibc-locale
# stopped generating binary locales it would drag in the localedef source
# tree under /usr/share/i18n (~4 MB). IMAGE_LINGUAS itself stays effective
# for the gettext catalogs of userland tools.
LINGUAS_INSTALL = ""

IMAGE_FEATURES += "package-management allow-empty-password empty-root-password allow-root-login"

inherit image

do_package_index[nostamp] = "1"
do_package_index[depends] += "${PACKAGEINDEXDEPS}"

python do_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}
addtask do_package_index after do_rootfs before do_image_complete
