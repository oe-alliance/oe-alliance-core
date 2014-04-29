# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "xtrend"
MACHINE_BRAND = "Xtrend"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    et-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-etxx00"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "et-blindscan-dvbs-utils"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "


MACHINE_FEATURES += "3dtv alsa blindscan-dvbs hdmicec switchoff wifi bootsplash"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

EXTRA_IMAGEDEPENDS += " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "

MACHINE_EXTRA_RDEPENDS_${PN} = " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "
