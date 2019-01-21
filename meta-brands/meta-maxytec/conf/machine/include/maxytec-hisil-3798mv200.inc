MACHINE_FEATURES += "qthbbtv qtstalker"

PREFERRED_PROVIDER_virtual/egl ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libopencl ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgbm ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "maxytec-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "maxytec-dvb-modules-${MACHINE}"

# Qt
PACKAGECONFIG_GL_pn-qtbase = "no-opengl linuxfb"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-${HICHIPSET}', 'maxytec-bootlogo-${MACHINE} maxytec-libs-${HICHIPSET} maxytec-param-${MACHINE} maxytec-bootargs-${MACHINE} maxytec-fastboot-${MACHINE}' , '', d)} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'maxytec-mali-${HICHIPSET} kernel-module-mali-${HICHIPSET}' , '', d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-3798mv200', 'ffmpeg gstreamer1.0-libav' , 'gstreamer1.0-libav ', d)} \
    "

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-maxytec = "4.4.35"

IMAGE_CMD_maxytecfastboot8gb_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}; \
    cp ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.fastboot8gb.gz ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.fastboot.gz; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/uImage; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.tar; \
    cp ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/update_bootargs_${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/update_bootargs_${MACHINE}.bin; \
    cp ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/pq_param.bin; \
    cp ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${IMAGEVERSION}" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    zip -r ../${IMAGE_NAME}_usb.zip *; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    cd ..; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}; \
    "


require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/maxytec-oem.inc