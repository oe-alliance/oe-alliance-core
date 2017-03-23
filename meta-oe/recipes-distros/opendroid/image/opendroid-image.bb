SUMMARY = "openDroid Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "OpenDroid Team"

require conf/license/license-gplv2.inc

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"
PV = "${IMAGE_VERSION}"
PR = "r${PR_NUM}"

DEPENDS = "zip-native"

IMAGE_INSTALL = "opendroid-base \
    ${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "", \
    " \
    packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    packagegroup-base-nfs \
    ", d)} \
    "

export IMAGE_BASENAME = "opendroid-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image

image_preprocess() {
    curdir=$PWD
    cd ${IMAGE_ROOTFS}

    # because we're so used to it
    ln -s opkg usr/bin/ipkg || true
    ln -s opkg-cl usr/bin/ipkg-cl || true

    cd $curdir

    # Speedup boot by reducing the host key size. The time it takes grows
    # exponentially by key size, the default is 2k which takes several
    # seconds on most boxes.
    echo 'DROPBEAR_RSAKEY_ARGS="-s 1024"' >> ${IMAGE_ROOTFS}${sysconfdir}/default/dropbear
}

IMAGE_PREPROCESS_COMMAND += "image_preprocess; "
