DISTRO_FEATURES_remove = "x11 wayland"

include conf/machine/include/dags-oem.inc

KERNEL_FILE = "oe_kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"
MTD_BLACK  = "mmcblk0"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
IMAGE_FSTYPES =+ "tar.bz2"

require conf/machine/include/tune-cortexa15.inc

IMAGE_CMD_tar_prepend = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}_initrd_run.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/initrd_run.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${IMAGE_ROOTFS}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.rootfs.tar.bz2; \
    rm -f ${DEPLOY_DIR_IMAGE}/.tar.bz2; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf update; \
"

EXTRA_IMAGEDEPENDS = "\
    dags-initrd-cfe-${MACHINEBUILD}"
