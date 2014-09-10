SUMMARY = "ViX-HD version info"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "ViX team"
LICENSE = "proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

URL = "http://vixhd.dyndns.org"

S = "${WORKDIR}"

inherit autotools

PACKAGES = "${PN}"

do_install() {
    # generate /etc/image-version
    install -d ${D}/etc
    echo "box_type=${MACHINEBUILD}" > ${D}/etc/image-version
    echo "build_type=${BUILDTYPE}" >> ${D}/etc/image-version
    echo "version=${IMAGE_VERSION}" >> ${D}/etc/image-version
    echo "build=${BUILD_VERSION}" >> ${D}/etc/image-version
    echo "date=${DATETIME}" >> ${D}/etc/image-version
    echo "comment=ViX" >> ${D}/etc/image-version
    echo "target=9" >> ${D}/etc/image-version
    echo "creator=openViX" >> ${D}/etc/image-version
    echo "url=${URL}" >> ${D}/etc/image-version
    echo "catalog=${URL}" >> ${D}/etc/image-version
}

FILES_${PN} = "/etc/image-version"

