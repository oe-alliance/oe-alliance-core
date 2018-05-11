require conf/machine/include/gigablue-oem.inc

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
IMAGE_FSTYPES =+ "tar.bz2"

require conf/machine/include/tune-cortexa15.inc

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "gigablue-blindscan-utils-arm"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "gigablue-blindscan-utils-arm"

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-amr --with-pcm --with-dts --with-eac3 \
    ${@bb.utils.contains('MACHINE', 'gb7252', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
"

MACHINE_FEATURES += " mmc emmc swap"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

IMAGE_CMD_tar_prepend = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}_boot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/boot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}_gpt.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/gpt.bin; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}_rescue.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rescue.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${IMAGE_ROOTFS}; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.rootfs.tar.bz2; \
    rm -f ${DEPLOY_DIR_IMAGE}/.tar.bz2; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf gigablue; \
"

