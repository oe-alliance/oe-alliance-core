SUMMARY = "teamBlue version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "teamBlue"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINEBUILD}"

URL = "http://www.teamblue.tech"

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
    echo "comment=teamBlue" >> ${D}/etc/image-version
    echo "target=9" >> ${D}/etc/image-version
    echo "creator=teamBlue" >> ${D}/etc/image-version
    echo "url=${URL}" >> ${D}/etc/image-version
    echo "catalog=${URL}" >> ${D}/etc/image-version
    echo "${MACHINE}" > ${D}/etc/model
}
do_install[vardepsexclude] += "DATETIME"

FILES_${PN} += "/etc/model /etc/image-version"

