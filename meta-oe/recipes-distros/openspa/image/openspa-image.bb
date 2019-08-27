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
