PACKAGECONFIG_GL_pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB_pn-qtbase = " "

# We know about rtc
include conf/machine/include/build-extras.inc

DISTRO_FEATURES_remove = "x11 wayland"

BRAND_OEM = "beyonwiz"

IMAGEDIR = "beyonwiz/v2"

MACHINE_NAME = "V2"

MACHINE_BRAND = "Beyonwiz"

MTD_KERNEL = "mmcblk0p12"

MTD_ROOTFS = "mmcblk0p16"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"
MTD_BLACK  = "mmcblk0"

VER = "-beyonwizv2"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    beyonwiz-dvb-modules-${MACHINE} \
    beyonwiz-loadmodules-${MACHINE} \ 
    beyonwiz-opengl-${MACHINE} \
    beyonwiz-libs-${MACHINE} \
    beyonwiz-hihalt-${MACHINE} \
    beyonwiz-reader-${MACHINE} \
    beyonwiz-libreader-${MACHINE} \
    beyonwiz-partitions-${MACHINE} \
    beyonwiz-tntfs-${HICHIPSET} \
    beyonwiz-buildimage \
    e2fsprogs-resize2fs \
    gptfdisk \
"

MACHINE_FEATURES += "hisil switchoff 7segment hdmicec skins1080 emmc mmc dvbc-only kodi19 alsa hbbtv-browser-webkit qtstalker qtnetflix"

IMAGE_CLASSES  += "beyonwiz-emmc_image"
IMAGE_FSTYPES += "beyonwizemmc"
IMAGE_ROOTFS_SIZE ?= "524288"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

PREFERRED_VERSION_linux-beyonwiz = "4.4.35"

PREFERRED_PROVIDER_virtual/kernel = "linux-beyonwiz"
PREFERRED_PROVIDER_virtual/egl = "beyonwiz-opengl-${MACHINE}" 
PREFERRED_PROVIDER_virtual/libgles1 = "beyonwiz-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "beyonwiz-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "beyonwiz-blindscan-${MACHINE}"

PREFERRED_RPROVIDER_showiframe = "beyonwiz-showiframe-${MACHINE}"
PREFERRED_RPROVIDER_aio-grab = "beyonwiz-grab-${MACHINE}"

require conf/machine/include/tune-cortexa15.inc

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    enigma2-plugin-drivers-dvb-usb-it913x \
    enigma2-plugin-drivers-dvb-usb-rtl2832 \
    enigma2-plugin-drivers-ct2-dvb-usb-dualhd \
    enigma2-plugin-picons-tv-australia \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

DVBMEDIASINK_CONFIG = "--with-h265 --with-vb8 --with-vb9 --with-spark --with-wma --with-wmv --with-pcm --with-dts --with-eac3"

IMAGE_CMD_beyonwizemmc_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/rootfs.ext4; \
    cd ${IMAGE_ROOTFS}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${KERNEL_FILE}; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp -f ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/logo.img; \
    fi; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions; \
    mkupdate -s 00000003-00000001-01010101 -f ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/emmc_partitions.xml -d ${DEPLOY_DIR_IMAGE}/usb_update.bin; \ 
    cd ${DEPLOY_DIR_IMAGE}; \
    cp ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.manifest ${IMAGE_NAME}_usb.zip.manifest; \
    zip -j ${IMAGE_NAME}_recovery_emmc.zip ${DEPLOY_DIR_IMAGE}/usb_update.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/apploader.bin; \
    zip ${IMAGE_NAME}_mmc.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ext4; \
    rm -f -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/usb_update.bin; \
    rm -Rf ${IMAGEDIR}; \
    "

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-fulan/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
BBMASK += "meta-oe/recipes-qt5.14/"
