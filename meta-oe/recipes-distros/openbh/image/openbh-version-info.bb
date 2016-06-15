SUMMARY = "OpenBH version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Blackhole"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINEBUILD}"

URL = "http://www.vuplus-community.net"

S = "${WORKDIR}"

inherit autotools

PACKAGES = "${PN}"

do_install() {
    if [ "${DISTRO_TYPE}" = "experimental" ] ; then
        BUILDTYPE="1"
    else
        BUILDTYPE="0"
    fi
    # generate /etc/image-version
    install -d ${D}/etc
    echo "Creator = OpenViX" > ${D}/etc/image-version
    echo "Version = ${IMAGE_VERSION}" >> ${D}/etc/image-version
    echo "Build = ${BUILD_VERSION}" >> ${D}/etc/image-version
    echo "Type = ${DISTRO_TYPE}" >> ${D}/etc/image-version
    echo "Machine = ${MACHINEBUILD}" >> ${D}/etc/image-version
    echo "URL = ${URL}" >> ${D}/etc/image-version
}

FILES_${PN} += "/etc/model /etc/image-version /etc/oe-git.log /etc/e2-git.log"

