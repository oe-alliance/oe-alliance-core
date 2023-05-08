SUMMARY = "OpenSPA version info"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "OpenSPA Team"

require conf/license/license-gplv2.inc

deltask fetch
deltask unpack
deltask patch
deltask prepare_recipe_sysroot
deltask configure
deltask compile

PV = "${IMAGE_VERSION}"
PR = "${BUILD_VERSION}"
PACKAGE_ARCH = "${MACHINEBUILD}"

SSTATE_SKIP_CREATION = "1"

URL = "https://openspa.info"

# if DATE in PR changes (next day), workdir name changes too
# this makes sstate unhappy and breakes many tasks in many weird ways

WORKDIR = "${TMPDIR}/work/${MULTIMACH_TARGET_SYS}/${PN}/${EXTENDPE}${PV}"

PACKAGES = "${PN}"

do_install[nostamp] = "1"

do_install() {
    install -d ${D}${sysconfdir}
    printf "box_type=${MACHINEBUILD}\n" > ${D}${sysconfdir}/image-version
    printf "build_type=${BUILDTYPE}\n" >> ${D}${sysconfdir}/image-version
    printf "machine_brand=${MACHINE_BRAND}\n" >> ${D}${sysconfdir}/image-version
    printf "machine_name=${MACHINE_NAME}\n" >> ${D}${sysconfdir}/image-version
    printf "version=${IMAGE_VERSION}\n" >> ${D}${sysconfdir}/image-version
    printf "build=${BUILD_VERSION}\n" >> ${D}${sysconfdir}/image-version
    printf "date=${DATE}\n" >> ${D}${sysconfdir}/image-version
    printf "comment=openSPA\n" >> ${D}${sysconfdir}/image-version
    printf "target=9\n" >> ${D}${sysconfdir}/image-version
    printf "creator=openSPA\n" >> ${D}${sysconfdir}/image-version
    printf "url=${URL}\n" >> ${D}${sysconfdir}/image-version
    printf "catalog=${URL}\n" >> ${D}${sysconfdir}/image-version
    printf "oever=${OE_VER}\n" >> ${D}${sysconfdir}/image-version
    printf "distro=${DISTRO_NAME}\n" >> ${D}${sysconfdir}/image-version
    printf "brandoem=${BRAND_OEM}\n" >> ${D}${sysconfdir}/image-version
    printf "machinemake=${MACHINEBUILD}\n" >> ${D}${sysconfdir}/image-version
    printf "imageversion=${DISTRO_VERSION}\n" >> ${D}${sysconfdir}/image-version
    printf "imagebuild=${BUILD_VERSION}\n" >> ${D}${sysconfdir}/image-version
    printf "imagedevbuild=${DEVELOPER_BUILD_VERSION}\n" >> ${D}${sysconfdir}/image-version
    printf "imagetype=${DISTRO_TYPE}\n" >> ${D}${sysconfdir}/image-version
    printf "feedsurl=${DISTRO_FEED_URI}\n" >> ${D}${sysconfdir}/image-version
    printf "imagedir=${IMAGEDIR}\n" >> ${D}${sysconfdir}/image-version
    printf "imagefs=${IMAGE_FSTYPES}\n" >> ${D}${sysconfdir}/image-version
    printf "mtdrootfs=${MTD_ROOTFS}\n" >> ${D}${sysconfdir}/image-version
    printf "mtdkernel=${MTD_KERNEL}\n" >> ${D}${sysconfdir}/image-version
    printf "rootfile=${ROOTFS_FILE}\n" >> ${D}${sysconfdir}/image-version
    printf "kernelfile=${KERNEL_FILE}\n" >> ${D}${sysconfdir}/image-version
    printf "mkubifs=${MKUBIFS_ARGS}\n" >> ${D}${sysconfdir}/image-version
    printf "ubinize=${UBINIZE_ARGS}\n" >> ${D}${sysconfdir}/image-version
    printf "driverdate=${DRIVERSDATE}\n" >> ${D}${sysconfdir}/image-version
    printf "arch=${TUNE_PKGARCH}\n" >> ${D}${sysconfdir}/image-version
    printf "display-type=${DISPLAY_TYPE}\n" >> ${D}${sysconfdir}/image-version
    printf "hdmi=${HAVE_HDMI}\n" >> ${D}${sysconfdir}/image-version
    printf "yuv=${HAVE_YUV}\n" >> ${D}${sysconfdir}/image-version
    printf "rca=${HAVE_RCA}\n" >> ${D}${sysconfdir}/image-version
    printf "av-jack=${HAVE_AV_JACK}\n" >> ${D}${sysconfdir}/image-version
    printf "scart=${HAVE_SCART}\n" >> ${D}${sysconfdir}/image-version
    printf "scart-yuv=${HAVE_SCART_YUV}\n" >> ${D}${sysconfdir}/image-version
    printf "dvi=${HAVE_DVI}\n" >> ${D}${sysconfdir}/image-version
    printf "minitv=${HAVE_MINITV}\n" >> ${D}${sysconfdir}/image-version
    printf "hdmi-in-hd=${HAVE_HDMI_IN_HD}\n" >> ${D}${sysconfdir}/image-version
    printf "hdmi-in-fhd=${HAVE_HDMI_IN_FHD}\n" >> ${D}${sysconfdir}/image-version
    printf "wol=${HAVE_WOL}\n" >> ${D}${sysconfdir}/image-version
    printf "wwol=${HAVE_WWOL}\n" >> ${D}${sysconfdir}/image-version
    printf "ci=${HAVE_CI}\n" >> ${D}${sysconfdir}/image-version
    printf "transcoding=${TRANSCODING}\n" >> ${D}${sysconfdir}/image-version
    printf "${MACHINE}\n" > ${D}${sysconfdir}/model
    printf "compile-date=${DATE}" >> ${D}/etc/image-version
    printf "compile-datetime=${DATETIME}" >> ${D}/etc/image-version
}

do_install[vardepsexclude] += "DATE DATETIME"

FILES:${PN} += "${sysconfdir}/image-version ${sysconfdir}/model"
