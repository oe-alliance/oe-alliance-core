SUMMARY = "Beyonwiz version info"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "Beyonwiz"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"

URL = "http://www.beyonwiz.com.au"

S = "${WORKDIR}"

PACKAGES = "${PN}"

FILES_${PN} += "/etc/image-version"

do_build[nostamp] = "1"
do_clean[nostamp] = "1"
do_package[nostamp] = "1"
do_packagedata[nostamp] = "1"
do_populate_sysroot[nostamp] = "1"
do_install[nostamp] = "1"
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
    echo "comment=Beyonwiz" >> ${D}/etc/image-version
    echo "target=9" >> ${D}/etc/image-version
    echo "creator=openEasyGUI" >> ${D}/etc/image-version
    echo "url=${URL}" >> ${D}/etc/image-version
    echo "catalog=${URL}" >> ${D}/etc/image-version
}
