SUMMARY = "openSPA Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openSPA team"

require conf/license/license-gplv2.inc

REPRODUCIBLE_TIMESTAMP_ROOTFS = "${@int(time.time()-10000)}"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"

do_rootfs[deptask] = "do_rm_work"

SSTATE_ALLOW_OVERLAP_FILES += "${DEPLOY_DIR_IMAGE}"

IMAGE_INSTALL = "openspa-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "", \
    " \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-nfs \
    ", d)} \
    "

# Some additional comfort on the shell: Pre-install nano on boxes with 128 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96", "", "nano", d)}"

# ... plus mc and helpers on 256 MB or more:
IMAGE_INSTALL += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "mc mc-fish mc-helpers", d)}"

export IMAGE_BASENAME = "openspa-image"

# Prevent locales-archive creation to save some space
IMAGE_LOCALES_ARCHIVE = ""

# 64 or 128MB of flash: No language files, above: Spanish and French
IMAGE_LINGUAS  = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "es-es fr-fr", d)}"

# Add more languages for 512 or more MB of flash:
IMAGE_LINGUAS += "${@bb.utils.contains_any("FLASHSIZE", "64 96 128 256", "", "de-de it-it nl-nl pt-pt", d)}"

IMAGE_FEATURES += "package-management"

INHIBIT_DEFAULT_DEPS = "1"

inherit image

image_preprocess() {
    # set root password=openspa
    sed 's%^root:[^:]*:%root:$6$jnyebF6/$ayLqhrLW/R/9eHfhJWkUY3Wzwg2CIQ8p1HgfLDY6D6eDhUS6n.Ke6u6LN2BioDRNJLqf83RLMrT1YSEqzXcx20:%' < ${IMAGE_ROOTFS}/etc/shadow > ${IMAGE_ROOTFS}/etc/shadow.new
    mv ${IMAGE_ROOTFS}/etc/shadow.new ${IMAGE_ROOTFS}/etc/shadow
}

IMAGE_PREPROCESS_COMMAND += "image_preprocess; "

do_package_index[nostamp] = "1"
do_package_index[depends] += "${PACKAGEINDEXDEPS}"

python do_package_index() {
    from oe.rootfs import generate_index_files
    generate_index_files(d)
}
addtask do_package_index after do_rootfs before do_image_complete
