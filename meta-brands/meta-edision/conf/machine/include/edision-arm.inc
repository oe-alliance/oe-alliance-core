
PACKAGECONFIG_GL_pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB_pn-qtbase = " "

PREFERRED_VERSION_linux-edision = "5.5"


# eMMC has 8 GB in total, but one image gets only 2 GB of that (Partitioned eMMC)
FLASHSIZE = "2048"

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts=no --with-dtsdownmix --with-eac3 --with-h265 --with-vb9"

# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "edision"
MACHINE_BRAND = "Edision"
IMAGEDIR = "${MACHINE}"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    edision-dvb-modules-${MACHINE} \
    resizerootfs \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'swap', 'edision-swapcreate', '',d)} \
    gstreamer1.0-libav \
    "

include conf/machine/include/autoload-filesystems.inc

PREFERRED_PROVIDER_virtual/kernel = "linux-edision"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "edision-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "edision-blindscan-utils-${MACHINE}"

GCCVERSION = "8.%"

KERNEL_IMAGETYPE = "Image.gz"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_CLASSES += "osmio4k-emmcimg"
IMAGE_FSTYPES ?= "emmcimg"
IMAGE_OVERHEAD_FACTOR = "1.0"

IMAGE_CMD_emmcimg_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.emmc ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/emmc.img; \
    cd ${IMAGE_ROOTFS}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cp ${DEPLOY_DIR_IMAGE}/Image.gz ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "

MACHINE_FEATURES += "alsa 3dtv switchoff hdmicec bootsplash omb blindscan-tbs linuxwifi linux4extra wifiusbmodule mmc emmc"

BOOTLOGO_FILENAME = "splash.bin"

DEFAULTTUNE = "aarch64"
require conf/machine/include/arm/arch-armv8.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc
require conf/machine/multilib.inc
