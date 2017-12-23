IMAGE_CLASSES += "ceryon-emmc_image_multi"

MACHINE_FEATURES += "qthbbtv qtstalker"
DISTRO_FEATURES_remove = "directfb gtk-directfb"

IMAGE_FSTYPES += "ceryon-emmc"
IMAGE_ROOTFS_SIZE ?= "294912"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

PREFERRED_VERSION_linux-ceryon= "4.10.12"
PREFERRED_VERSION_linux-libc-headers = "4.10"

IMAGE_CMD_ceryon-emmc_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "lcd220", "cp ${DEPLOY_DIR_IMAGE}/lcdwarning220.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdflashing.bmp;" , "", d)} \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.emmc.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/disk.img; \
    cd ${IMAGE_ROOTFS}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cp ${DEPLOY_DIR_IMAGE}/zImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "

require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/ceryon-oem.inc