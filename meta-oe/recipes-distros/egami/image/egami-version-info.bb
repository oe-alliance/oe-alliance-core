SUMMARY = "EGAMI version info"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "EGAMI team"
LICENSE = "proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

URL = "http://egami-image.com"

S = "${WORKDIR}"

PACKAGES = "${PN}"

do_install() {
    if [ "${DISTRO_TYPE}" = "experimental" ] ; then
        BUILDTYPE="1"
    else
        BUILDTYPE="0"
    fi
    # generate ${sysconfdir}/image-version
    install -d ${D}${sysconfdir}
    echo "box_type=${MACHINEBUILD}" > ${D}${sysconfdir}/image-version
    echo "build_type=${BUILDTYPE}" >> ${D}${sysconfdir}/image-version
    echo "machine_brand=${MACHINE_BRAND}" >> ${D}${sysconfdir}/image-version
    echo "machine_name=${MACHINE_NAME}" >> ${D}${sysconfdir}/image-version
    echo "version=${IMAGE_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "build=${BUILD_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "date=${DATETIME}" >> ${D}${sysconfdir}/image-version
    echo "comment=EGAMI" >> ${D}${sysconfdir}/image-version
    echo "target=9" >> ${D}${sysconfdir}/image-version
    echo "creator=EGAMI" >> ${D}${sysconfdir}/image-version
    echo "url=${URL}" >> ${D}${sysconfdir}/image-version
    echo "catalog=${URL}" >> ${D}${sysconfdir}/image-version
}
do_install[vardepsexclude] += "DATETIME"

FILES_${PN} = "${sysconfdir}/image-version"