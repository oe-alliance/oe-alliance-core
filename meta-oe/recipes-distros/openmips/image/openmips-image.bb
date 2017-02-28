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

# FIX distro-image.bb ERROR: Taskhash mismatch - part 1 add packages to build dependencies of distro-image.bb which run on end of build process
DEPENDS = " \
    oe-alliance-base \
    oe-alliance-enigma2 \
    oe-alliance-wifi \
    oe-alliance-feeds \
    enigma2-plugins \
    ${DISTRO}-base \
    ${DISTRO}-version-info \
    "

# FIX distro-image.bb ERROR: Taskhash mismatch - part 2  make sure all do_rm_work tasks of build dependencies are finished before starting do_rootfs of distro-image.bb
do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "openmips-base"

export IMAGE_BASENAME = "openmips-image"
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
