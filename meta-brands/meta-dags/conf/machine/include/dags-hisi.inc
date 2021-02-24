include conf/machine/include/dags-oem.inc

DISTRO_FEATURES_remove = "x11 wayland"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"
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
	dags-buildimage \
	deepsleep-${SOC_FAMILY} \
"

MACHINE_FEATURES += "switchoff hdmicecskins1080 emmc mmc alsa 7segment hdmicec skins1080 kodi18 hbbtv-browser-webkit"

IMAGE_CLASSES += "dags-emmc_image"
IMAGE_FSTYPES += "dagsemmc"
IMAGE_ROOTFS_SIZE ?= "524288"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

PREFERRED_VERSION_linux-dags = "4.4.35"
PREFERRED_VERSION_ffmpeg = "3.4.2"

PREFERRED_PROVIDER_virtual/kernel = "linux-dags"

PREFERRED_PROVIDER_virtual/egl = "dags-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgles1 = "dags-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgles2 = "dags-opengl-${SOC_FAMILY}"

require conf/machine/include/tune-cortexa15.inc

IMAGE_CMD_dagsemmc_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/release/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
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
BBMASK += "meta-fulan/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
BBMASK += "meta-oe/recipes-qt5.14/"
