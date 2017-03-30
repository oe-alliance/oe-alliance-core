SUMMARY = "OpenMips Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "openMips team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PR .= "-r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "openmips-base"

export IMAGE_BASENAME = "openmips-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image

image_preprocess() {
    # Speedup boot by reducing the host key size. The time it takes grows
    # exponentially by key size, the default is 2k which takes several
    # seconds on most boxes.
    echo 'DROPBEAR_RSAKEY_ARGS="-s 1024"' >> ${IMAGE_ROOTFS}${sysconfdir}/default/dropbear
}

IMAGE_PREPROCESS_COMMAND += "image_preprocess; "
