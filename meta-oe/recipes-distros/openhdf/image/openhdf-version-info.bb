SUMMARY = "OpenHDF version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "OpenHDF"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINEBUILD}"

URL = "http://www.hdfreaks.cc"

S = "${WORKDIR}"

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
    echo "machine_brand=${MACHINE_BRAND}" >> ${D}/etc/image-version
    echo "machine_name=${MACHINE_NAME}" >> ${D}/etc/image-version
    echo "version=${DISTRO_VERSION}" >> ${D}/etc/image-version
    echo "build=${BUILD_VERSION}" >> ${D}/etc/image-version
    echo "date=${DATETIME}" >> ${D}/etc/image-version
    echo "comment=openHDF" >> ${D}/etc/image-version
    echo "target=9" >> ${D}/etc/image-version
    echo "creator=openHDF" >> ${D}/etc/image-version
    echo "url=${URL}" >> ${D}/etc/image-version
    echo "catalog=${URL}" >> ${D}/etc/image-version
    echo "oever=${OE_VER}" >> ${D}/etc/image-version
    echo "distro=${DISTRO_NAME}" >> ${D}/etc/image-version
    echo "brandoem=${BRAND_OEM}" >> ${D}/etc/image-version
    echo "machinemake=${MACHINEBUILD}" >> ${D}/etc/image-version
    echo "imageversion=${DISTRO_VERSION}" >> ${D}/etc/image-version
    echo "imagebuild=${BUILD_VERSION}" >> ${D}/etc/image-version
    echo "imagedevbuild=${DEVELOPER_BUILD_VERSION}" >> ${D}/etc/image-version
    echo "imagetype=${DISTRO_TYPE}" >> ${D}/etc/image-version
    echo "feedsurl=${DISTRO_FEED_URI}" >> ${D}/etc/image-version
    echo "imagedir=${IMAGEDIR}" >> ${D}/etc/image-version
    echo "imagefs=${IMAGE_FSTYPES}" >> ${D}/etc/image-version
    echo "mtdrootfs=${MTD_ROOTFS}" >> ${D}/etc/image-version
    echo "mtdkernel=${MTD_KERNEL}" >> ${D}/etc/image-version
    echo "rootfile=${ROOTFS_FILE}" >> ${D}/etc/image-version
    echo "kernelfile=${KERNEL_FILE}" >> ${D}/etc/image-version
    echo "mkubifs=${MKUBIFS_ARGS}" >> ${D}/etc/image-version
    echo "ubinize=${UBINIZE_ARGS}" >> ${D}/etc/image-version
    echo "driverdate=${DRIVERSDATE}" >> ${D}/etc/image-version
    echo "arch=${DEFAULTTUNE}" >> ${D}/etc/image-version
    echo "display-type=${DISPLAY_TYPE}" >> ${D}/etc/image-version
    echo "hdmi=${HAVE_HDMI}" >> ${D}/etc/image-version
    echo "yuv=${HAVE_YUV}" >> ${D}/etc/image-version
    echo "rca=${HAVE_RCA}" >> ${D}/etc/image-version
    echo "av-jack=${HAVE_AV_JACK}" >> ${D}/etc/image-version
    echo "scart=${HAVE_SCART}" >> ${D}/etc/image-version
    echo "scart-yuv=${HAVE_SCART_YUV}" >> ${D}/etc/image-version
    echo "dvi=${HAVE_DVI}" >> ${D}/etc/image-version
    echo "minitv=${HAVE_MINITV}" >> ${D}/etc/image-version
    echo "hdmi-in-hd=${HAVE_HDMI_IN_HD}" >> ${D}/etc/image-version
    echo "hdmi-in-fhd=${HAVE_HDMI_IN_FHD}" >> ${D}/etc/image-version
    echo "wol=${HAVE_WOL}" >> ${D}/etc/image-version
    echo "wwol=${HAVE_WWOL}" >> ${D}/etc/image-version
    echo "ci=${HAVE_CI}" >> ${D}/etc/image-version
    echo "transcoding=${TRANSCODING}" >> ${D}/etc/image-version
    echo "${MACHINE}" > ${D}/etc/model
    echo "compile-date=${DATE}" >> ${D}/etc/image-version
    echo "compile-datetime=${DATETIME}" >> ${D}/etc/image-version
}
do_install[vardepsexclude] += "DATE DATETIME"

FILES_${PN} += "/etc/model /etc/image-version /etc/oe-git.log /etc/e2-git.log"

