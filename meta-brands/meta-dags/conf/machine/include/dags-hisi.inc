include conf/machine/include/dags-oem.inc

DISTRO_FEATURES:remove = "x11 wayland"

PACKAGECONFIG_GL:pn-qtbase = " "
PACKAGECONFIG:append:pn-qtbase = " gles2 linuxfb"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"
APPLOADER_FILE = "apploader.bin"
MTD_BLACK  = "mmcblk0"

MTD_KERNEL = "mmcblk0p27"
MTD_ROOTFS = "mmcblk0p30"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " \
	dags-reader-${SOC_FAMILY} \
	dags-libreader-${SOC_FAMILY} \
	dags-loadmodules-${SOC_FAMILY} \
	e2fsprogs-resize2fs \
	dags-libs-${SOC_FAMILY} \
	enigma2-plugin-drivers-network-usb-rtl8192eu \
	dags-bootoptions-${MACHINEBUILD} \
	dags-partitions-${MACHINEBUILD} \
	dags-buildimage \
	deepsleep-${SOC_FAMILY} \
	dags-blindscan-utils-${SOC_FAMILY} \
"

IMAGE_CLASSES += "dags-emmc_image"
IMAGE_FSTYPES += "dagsemmc"
IMAGE_ROOTFS_SIZE ?= "524288"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

PREFERRED_VERSION_linux-dags = "4.4.35"

PREFERRED_PROVIDER_virtual/kernel = "linux-dags"

PREFERRED_PROVIDER_virtual/egl = "dags-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgles1 = "dags-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgles2 = "dags-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgbm ?= "dags-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"
PREFERRED_RPROVIDER_aio-grab = "dags-grab-${SOC_FAMILY}"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dags-blindscan-utils-${SOC_FAMILY}"

require conf/machine/include/arm/armv7a/tune-cortexa15.inc

IMAGE_CMD:tar:prepend = "\
	mkdir -p ${IMAGE_ROOTFS}/usr/lib; \
	if [ "${PACKAGE_LIST}" = "1" ]; then \
        cp ${IMAGE_MANIFEST} ${IMAGE_ROOTFS}/usr/lib/package.lst; \
    fi; \
    "

IMAGE_CMD:dagsemmc:append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/release/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-${APPLOADER_FILE} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${APPLOADER_FILE}; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}_subrootfs.ext4 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.ext4; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_restore_mmc.zip ${IMAGEDIR}/*; \
    cd ${IMAGE_ROOTFS}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cd ${DEPLOY_DIR_IMAGE}; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    zip -j ${IMAGE_NAME}_mmc.zip ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar.bz2; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/rootfs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -f -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -Rf ${IMAGEDIR}; \
    rm -Rf release; \
    "

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"

