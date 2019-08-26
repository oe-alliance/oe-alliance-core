SUMMARY = "Beyonwiz version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Beyonwiz"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINEBUILD}"

URL = "https://beyonwiz.com.au/"

do_configure[nostamp] = "1"

S = "${WORKDIR}"

PACKAGES = "${PN}"

do_install() {
    install -d ${D}${sysconfdir}
    echo "box_type=${MACHINEBUILD}" > ${D}${sysconfdir}/image-version
    echo "build_type=${BUILDTYPE}" >> ${D}${sysconfdir}/image-version
    echo "machine_brand=${MACHINE_BRAND}" >> ${D}${sysconfdir}/image-version
    echo "machine_name=${MACHINE_NAME}" >> ${D}${sysconfdir}/image-version
    echo "version=${IMAGE_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "build=${BUILD_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "date=${DATETIME}" >> ${D}${sysconfdir}/image-version
    echo "comment=Beyonwiz" >> ${D}${sysconfdir}/image-version
    echo "target=9" >> ${D}${sysconfdir}/image-version
    echo "creator=Beyonwiz" >> ${D}${sysconfdir}/image-version
    echo "url=${URL}" >> ${D}${sysconfdir}/image-version
    echo "catalog=${URL}" >> ${D}${sysconfdir}/image-version
    echo "oever=${OE_VER}" >> ${D}${sysconfdir}/image-version
    echo "distro=${DISTRO_NAME}" >> ${D}${sysconfdir}/image-version
    echo "brandoem=${BRAND_OEM}" >> ${D}${sysconfdir}/image-version
    echo "machinemake=${MACHINEBUILD}" >> ${D}${sysconfdir}/image-version
    echo "imageversion=${DISTRO_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "imagebuild=${BUILD_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "imagedevbuild=${DEVELOPER_BUILD_VERSION}" >> ${D}${sysconfdir}/image-version
    echo "imagetype=${DISTRO_TYPE}" >> ${D}${sysconfdir}/image-version
    echo "feedsurl=${DISTRO_FEED_URI}" >> ${D}${sysconfdir}/image-version
    echo "imagedir=${IMAGEDIR}" >> ${D}${sysconfdir}/image-version
    echo "imagefs=${IMAGE_FSTYPES}" >> ${D}${sysconfdir}/image-version
    echo "mtdrootfs=${MTD_ROOTFS}" >> ${D}${sysconfdir}/image-version
    echo "mtdkernel=${MTD_KERNEL}" >> ${D}${sysconfdir}/image-version
    echo "rootfile=${ROOTFS_FILE}" >> ${D}${sysconfdir}/image-version
    echo "kernelfile=${KERNEL_FILE}" >> ${D}${sysconfdir}/image-version
    echo "mkubifs=${MKUBIFS_ARGS}" >> ${D}${sysconfdir}/image-version
    echo "ubinize=${UBINIZE_ARGS}" >> ${D}${sysconfdir}/image-version
    echo "driverdate=${DRIVERSDATE}" >> ${D}${sysconfdir}/image-version
    echo "arch=${DEFAULTTUNE}" >> ${D}${sysconfdir}/image-version
    echo "display-type=${DISPLAY_TYPE}" >> ${D}${sysconfdir}/image-version
    echo "hdmi=${HAVE_HDMI}" >> ${D}${sysconfdir}/image-version
    echo "yuv=${HAVE_YUV}" >> ${D}${sysconfdir}/image-version
    echo "rca=${HAVE_RCA}" >> ${D}${sysconfdir}/image-version
    echo "av-jack=${HAVE_AV_JACK}" >> ${D}${sysconfdir}/image-version
    echo "scart=${HAVE_SCART}" >> ${D}${sysconfdir}/image-version
    echo "scart-yuv=${HAVE_SCART_YUV}" >> ${D}${sysconfdir}/image-version
    echo "dvi=${HAVE_DVI}" >> ${D}${sysconfdir}/image-version
    echo "minitv=${HAVE_MINITV}" >> ${D}${sysconfdir}/image-version
    echo "hdmi-in-hd=${HAVE_HDMI_IN_HD}" >> ${D}${sysconfdir}/image-version
    echo "hdmi-in-fhd=${HAVE_HDMI_IN_FHD}" >> ${D}${sysconfdir}/image-version
    echo "wol=${HAVE_WOL}" >> ${D}${sysconfdir}/image-version
    echo "wwol=${HAVE_WWOL}" >> ${D}${sysconfdir}/image-version
    echo "ci=${HAVE_CI}" >> ${D}${sysconfdir}/image-version
    echo "transcoding=${TRANSCODING}" >> ${D}${sysconfdir}/image-version
    echo "${MACHINE}" > ${D}${sysconfdir}/model
}
do_install[vardepsexclude] += "DATETIME"

FILES_${PN} += "${sysconfdir}/model ${sysconfdir}/image-version ${sysconfdir}/oe-git.log ${sysconfdir}/e2-git.log"

