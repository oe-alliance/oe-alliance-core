SUMMARY = "OpenViX Version Info"
MAINTAINER = "OpenViX"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

URL = "http://www.world-of-satellite.com"

S = "${WORKDIR}"

PACKAGES = "${PN}"

do_install() {
    # generate /etc/image-version
    install -d ${D}/etc
    echo "Creator = OpenViX" > ${D}/etc/image-version
    echo "Version = ${IMAGE_VERSION}" >> ${D}/etc/image-version
    echo "Build = ${BUILD_VERSION}" >> ${D}/etc/image-version
    echo "Dev = ${DEVELOPER_BUILD_VERSION}" >> ${D}/etc/image-version
    echo "Type = ${DISTRO_TYPE}" >> ${D}/etc/image-version
    echo "Machine = ${MACHINEBUILD}" >> ${D}/etc/image-version
    echo "URL = ${URL}" >> ${D}/etc/image-version
}

FILES_${PN} = "/etc/image-version"
