MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_VERSION_linux-gfutures = "4.7.6"
PREFERRED_VERSION_linux-libc-headers = "4.7"

IMAGE_FSTYPES ?= "ubi"

IMAGE_CMD_ubi_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/boot; \
    rm -Rf ${IMAGEDIR}; \
    "


MACHINE_FEATURES += " bootsplash omb boxmodel feature1"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/gfutures-oem.inc