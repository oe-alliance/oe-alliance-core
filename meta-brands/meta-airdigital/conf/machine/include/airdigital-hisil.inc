MACHINE_FEATURES += "qthbbtv qtstalker"
DISTRO_FEATURES_remove = "x11 wayland directfb"

PREFERRED_PROVIDER_virtual/egl = "airdigital-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles1 = "airdigital-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "airdigital-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libopencl = "airdigital-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "airdigital-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "airdigital-dvb-modules-${MACHINE}"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

MTD_KERNEL = "mtd7"
MTD_ROOTFS = "mtd8"
KERNEL_FILE = "uImage"
ROOTFS_FILE = "rootfs.ubi"

IMAGE_FSTYPES += "ubi"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 8192"
UBINIZE_ARGS = "-m 2048 -p 128KiB"
UBI_VOLNAME = "rootfs"
CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-airdigital = "4.4.35"
PREFERRED_VERSION_linux-libc-headers = "4.4"

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/pq_param.bin; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${IMAGEVERSION}" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    zip -j ${IMAGE_NAME}_usb.zip ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/fastboot.bin; \
    zip -j ${IMAGE_NAME}_usb.zip ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/bootargs.bin; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "

require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/airdigital-oem.inc