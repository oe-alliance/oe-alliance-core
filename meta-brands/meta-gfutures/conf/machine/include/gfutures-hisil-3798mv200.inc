MACHINE_FEATURES += "qthbbtv qtstalker"

PREFERRED_PROVIDER_virtual/egl = "gfutures-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles1 = "gfutures-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "gfutures-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/libopencl = "gfutures-mali-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "gfutures-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "gfutures-dvb-modules-${MACHINE}"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-gfutures = "4.4.35"
PREFERRED_VERSION_linux-libc-headers = "4.4"

IMAGE_CMD_hdfastboot8gb_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}; \
    cp ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.hdfastboot8gb.gz ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.fastboot.gz; \
    cp ${IMGDEPLOYDIR}/bootoptions.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/bootoptions.img; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/uImage; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/pq_param.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${IMAGEVERSION}" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    zip -r ../${IMAGE_NAME}_usb.zip *; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    cd ..; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    "


require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/gfutures-oem.inc