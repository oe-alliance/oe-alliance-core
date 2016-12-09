SUMMARY = "TitanNit version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "TitanNit"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINEBUILD}"

URL = "http://www.aaf-digital.info"

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
    echo "box_type=${MACHINEBUILD}" > ${D}/etc/image-version
    echo "build_type=${BUILDTYPE}" >> ${D}/etc/image-version
    echo "version=${IMAGE_VERSION}" >> ${D}/etc/image-version
    echo "build=${BUILD_VERSION}" >> ${D}/etc/image-version
    echo "date=${DATETIME}" >> ${D}/etc/image-version
    echo "comment=openATV" >> ${D}/etc/image-version
    echo "target=9" >> ${D}/etc/image-version
    echo "creator=openATV" >> ${D}/etc/image-version
    echo "url=${URL}" >> ${D}/etc/image-version
    echo "catalog=${URL}" >> ${D}/etc/image-version
    echo "${MACHINE}" > ${D}/etc/model
}

FILES_${PN} += "/etc/model /etc/image-version /etc/oe-git.log /etc/e2-git.log"

