SUMMARY = "OpenEight version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "OpenEight"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

URL = "http://www.octagon-germany.de/"

S = "${WORKDIR}"

PACKAGES = "${PN}"

do_install() {
    if [ "${DISTRO_TYPE}" = "experimental" ] ; then
        BUILDTYPE="1"
    else
        BUILDTYPE="0"
    fi

    install -d ${D}${sysconfdir}
    # generate ${sysconfdir}/image-version
    echo "box_type=${MACHINEBUILD}" > ${D}${sysconfdir}/image-version
    echo "build_type=${BUILDTYPE}" >> ${D}${sysconfdir}/image-version
    echo "machine_brand=${MACHINE_BRAND}" >> ${D}${sysconfdir}/image-version
    echo "machine_name=${MACHINE_NAME}" >> ${D}${sysconfdir}/image-version
    echo "version=${IMAGE_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "build=${BUILD_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "date=${DATETIME}" >> ${D}${sysconfdir}/image-version
    echo "comment=OpenEight" >> ${D}${sysconfdir}/image-version
    echo "target=9" >> ${D}${sysconfdir}/image-version
    echo "creator=OpenEight" >> ${D}${sysconfdir}/image-version
    echo "url=${URL}" >> ${D}${sysconfdir}/image-version
    echo "catalog=${URL}" >> ${D}${sysconfdir}/image-version
    echo "${MACHINE}" > ${D}${sysconfdir}/model
}

FILES_${PN} += "${sysconfdir}"

