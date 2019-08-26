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
    # generate ${sysconfdir}/image-version
    install -d ${D}${sysconfdir}
    echo "Creator = OpenViX" > ${D}${sysconfdir}/image-version
    echo "Version = ${IMAGE_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "Build = ${BUILD_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "Dev = ${DEVELOPER_BUILD_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "Type = ${DISTRO_TYPE}" >> ${D}${sysconfdir}/image-version
    echo "Machine = ${MACHINEBUILD}" >> ${D}${sysconfdir}/image-version
    echo "URL = ${URL}" >> ${D}${sysconfdir}/image-version
}

FILES_${PN} = "${sysconfdir}/image-version"
