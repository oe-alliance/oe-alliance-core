require conf/machine/include/gigablue-oem.inc

DISTRO_FEATURES_remove = "x11 wayland"

MTD_BLACK  = "mmcblk0"

VER = "-gbmv200"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    gigablue-loadmodules-${MACHINE} \ 
    gigablue-opengl-${MACHINE} \
    gigablue-libs-${MACHINE} \
    gigablue-hihalt-${MACHINE} \
    gigablue-reader-${MACHINEBUILD} \
    gigablue-libreader-${MACHINE} \
    gigablue-partitions-${MACHINEBUILD} \
    gigablue-tntfs-${HICHIPSET} \
    gigablue-buildimage \
    e2fsprogs-resize2fs \
    gptfdisk \
"

MACHINE_FEATURES += "switchoff 7segment hdmicec skins1080 emmc mmc blindscan-dvbs kodi19 alsa hbbtv-browser-webkit qtstalker hisi qtnetflix"

IMAGE_CLASSES  += "gigablue-emmc_image"
IMAGE_FSTYPES += "gigablueemmc"
IMAGE_ROOTFS_SIZE ?= "524288"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

PREFERRED_VERSION_linux-gigablue = "4.4.35"

PREFERRED_PROVIDER_virtual/egl = "gigablue-opengl-${MACHINE}" 
PREFERRED_PROVIDER_virtual/libgles1 = "gigablue-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "gigablue-opengl-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "gigablue-blindscan-${MACHINE}"

PREFERRED_RPROVIDER_showiframe = "gigablue-showiframe-${MACHINE}"
PREFERRED_RPROVIDER_aio-grab = "gigablue-grab-${MACHINE}"

require conf/machine/include/tune-cortexa15.inc

DVBSKYPROVIDER = "kernel"

DVBMEDIASINK_CONFIG = "--with-h265 --with-vb8 --with-vb9 --with-spark --with-wma --with-wmv --with-pcm --with-dts --with-eac3"

IMAGE_CMD_gigablueemmc_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4 ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions/rootfs.ext4; \
    cd ${IMAGE_ROOTFS}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions/${KERNEL_FILE}; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp -f ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions/logo.img; \
    fi; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions; \
    mkupdate -s 00000003-00000001-01010101 -f ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions/emmc_partitions.xml -d ${DEPLOY_DIR_IMAGE}/usb_update.bin; \ 
    cd ${DEPLOY_DIR_IMAGE}; \
    cp ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.manifest ${IMAGE_NAME}_usb.zip.manifest; \
    zip -j ${IMAGE_NAME}_recovery_emmc.zip ${DEPLOY_DIR_IMAGE}/usb_update.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions/apploader.bin; \
    zip ${IMAGE_NAME}_mmc.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ext4; \
    rm -f -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/usb_update.bin; \
    rm -Rf ${IMAGEDIR}; \
    "

OEM_ARCH = "${MACHINEBUILD}"

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-fulan/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
BBMASK += "meta-oe/recipes-qt5.14/"
