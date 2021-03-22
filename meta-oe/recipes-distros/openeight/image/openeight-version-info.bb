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

    install -d ${D}/etc
    # generate /etc/image-version
    echo "box_type=${MACHINEBUILD}" > ${D}/etc/image-version
    echo "build_type=${BUILDTYPE}" >> ${D}/etc/image-version
    echo "machine_brand=${MACHINE_BRAND}" >> ${D}/etc/image-version
    echo "machine_name=${MACHINE_NAME}" >> ${D}/etc/image-version
    echo "version=${IMAGE_VERSION}" >> ${D}/etc/image-version
    echo "build=${BUILD_VERSION}" >> ${D}/etc/image-version
    echo "date=${DATETIME}" >> ${D}/etc/image-version
    echo "comment=OpenEight" >> ${D}/etc/image-version
    echo "target=9" >> ${D}/etc/image-version
    echo "creator=OpenEight" >> ${D}/etc/image-version
    echo "url=${URL}" >> ${D}/etc/image-version
    echo "catalog=${URL}" >> ${D}/etc/image-version
    echo "${MACHINE}" > ${D}/etc/model
    echo "distro=${DISTRO_NAME}" >> ${D}/etc/image-version
    echo "compile-date=${DATE}" >> ${D}/etc/image-version
    echo "compile-datetime=${DATETIME}" >> ${D}/etc/image-version
}
do_install[vardepsexclude] += "DATE DATETIME"

FILES_${PN} += "/etc"

