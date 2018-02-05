# We know about rtc
include conf/machine/include/build-extras.inc

DISTRO_FEATURES_remove = "x11 wayland directfb gtk-directfb"

BRAND_OEM = "dinobot"

IMAGEDIR = "dinobot/${MACHINE}"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'ECO 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'DINOBOT 4KL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'DINOBOT 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'mediabox 4k M7L' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'DINOBOT 4KSE' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'ANADOL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'Vivant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'JEPSSEN' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'Vivant' , '', d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'mmcblk0p9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'mmcblk0p10' , '', d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'anadol4k', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kl', 'mmcblk0p10' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4k', 'mmcblk1p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mediabox4k', 'mmcblk1p1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'dinobot4kse', 'mmcblk1p1' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"
MTD_BLACK  = "mmcblk0"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    dinobot-dvb-modules-${MACHINE} \
    dinobot-loadmodules-${MACHINE} \ 
    dinobot-opengl-${MACHINE} \
    dinobot-libs-${MACHINE} \
    dinobot-hihalt-${MACHINE} \
"

MACHINE_FEATURES += "videoenhancement switchoff  hdmicec linuxwifi 7segment textlcd skins1080 blindscan-tbs emmc mmc \
    ${@bb.utils.contains('MACHINEBUILD', 'anadol4k', '' , ' dvb-c', d)} \
    "

IMAGE_CLASSES  += "dinobot-emmc_image"
IMAGE_FSTYPES += "dinobotemmc"
IMAGE_ROOTFS_SIZE ?= "294912"

TARGET_ARCH = "arm"
DEFAULTTUNE = "armv7ahf-neon"

PREFERRED_VERSION_linux-dinobot = "4.4.35"
PREFERRED_VERSION_linux-libc-headers = "4.4"

require conf/machine/include/tune-cortexa15.inc

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc

DVBMEDIASINK_CONFIG = "--with-h265 --with-vb8 --with-vb9 --with-spark --with-opus --with-wma --with-wmv --with-pcm --with-dts --with-eac3"

PREFERRED_PROVIDER_virtual/libgles1 = "dinobot-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "dinobot-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/egl = "dinobot-opengl-${MACHINE}"

PREFERRED_PROVIDER_virtual/kernel = "linux-dinobot"
PREFERRED_RPROVIDER_showiframe = "dinobot-showiframe-${MACHINE}"
PREFERRED_RPROVIDER_aio-grab = "dinobot-grab-${MACHINE}"

IMAGE_CMD_dinobotemmc_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.emmc.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${IMAGE_NAME}.sdcard.img; \
    cd ${IMAGE_ROOTFS}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_mmc.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -f -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -Rf ${IMAGEDIR}; \
    "


OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
