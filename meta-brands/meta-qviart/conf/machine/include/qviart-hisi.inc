PACKAGECONFIG_GL:pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

# We know about rtc
include conf/machine/include/build-extras.inc

DISTRO_FEATURES:remove = "x11 wayland"

BRAND_OEM = "qviart"

IMAGEDIR = "qviart/${MACHINE}"

MACHINE_BRAND = "Qviart"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"
MTD_BLACK  = "mmcblk0"

VER = "-og2ott4k"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    qviart-dvb-modules-${MACHINE} \
    qviart-loadmodules-${SOC_FAMILY} \ 
    qviart-opengl-${SOC_FAMILY} \
    qviart-libs-${MACHINE} \
    qviart-hihalt-${SOC_FAMILY} \
    qviart-reader-${MACHINE} \
    qviart-libreader-${SOC_FAMILY} \
    qviart-partitions-${MACHINE} \
    qviart-tntfs-${HICHIPSET} \
    qviart-buildimage \
    e2fsprogs-resize2fs \
    gptfdisk \
"

MACHINE_FEATURES += "hisil switchoff hdmicec skins1080 emmc mmc kodi20 alsa hbbtv-browser-webkit qtstalker qtnetflix openhbbtv hisichromium mali HDMISTANDBY"

IMAGE_CLASSES  += "qviart-emmc2_image"
IMAGE_FSTYPES += "qviartemmc2"
IMAGE_ROOTFS_SIZE ?= "624288"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

PREFERRED_VERSION_linux-qviart = "${LINUX_VERSION}"
PREFERRED_PROVIDER_virtual/kernel = "linux-qviart"
PREFERRED_VERSION_wpa-supplicant = "${@bb.utils.contains('MACHINE', 'og2s4k', '2.9' , '2.10', d)}"
PREFERRED_PROVIDER_virtual/egl = "qviart-opengl-${SOC_FAMILY}" 
PREFERRED_PROVIDER_virtual/libgles1 = "qviart-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/libgles2 = "qviart-opengl-${SOC_FAMILY}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "qviart-blindscan-${SOC_FAMILY}"

PREFERRED_RPROVIDER_showiframe = "qviart-showiframe-${SOC_FAMILY}"
PREFERRED_RPROVIDER_aio-grab = "qviart-grab-${SOC_FAMILY}"

require conf/machine/include/arm/armv7a/tune-cortexa15.inc

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    ${@bb.utils.contains('MACHINE', 'og2ott4k', 'enigma2-plugin-extensions-gbipboxclient enigma2-plugin-systemplugins-satipclient' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'og2s4k', 'firmware-uwe5621' , '', d)} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

DVBMEDIASINK_CONFIG = "--with-h265 --with-vb8 --with-vb9 --with-spark --with-wma --with-wmv --with-pcm --with-dts --with-eac3"

IMAGE_CMD:qviartemmc2:append = "\
	mkdir -p ${IMAGE_ROOTFS}/usr/lib; \
	if [ "${PACKAGE_LIST}" = "1" ]; then \
        cp ${IMAGE_MANIFEST} ${IMAGE_ROOTFS}/usr/lib/package.lst; \
    fi; \
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
    if [ -e ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/6605s.upg ]; then \
        zip -j ${IMAGE_NAME}_recovery_emmc.zip ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/6605s.upg; \
    fi; \
    zip ${IMAGE_NAME}_mmc.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ext4; \
    rm -f -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/usb_update.bin; \
    rm -Rf ${IMAGEDIR}; \
    rm -Rf ${IMGDEPLOYDIR}/userdata; \
    "

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"

