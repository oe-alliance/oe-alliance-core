require conf/machine/include/soc-family.inc
include conf/machine/include/autoload-filesystems.inc

# We know about rtc
include conf/machine/include/build-extras.inc


PREFERRED_PROVIDER_virtual/libgles1 = "wetek-opengl-amlogic"
PREFERRED_PROVIDER_virtual/libgles2 = "wetek-opengl-amlogic"
PREFERRED_PROVIDER_virtual/egl = "wetek-opengl-amlogic"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"

EXTRA_OECONF_append_pn-enigma2 = " --with-alsa --with-alsacard=hw:0 --with-alsamixer=Master --with-fbdev=/dev/fb2 --with-lcddev=/dev/null --with-amlogic"
DEPENDS_append_pn-enigma2 = " libdvbcsa wetek-libamcodec-${MACHINE} wetek-libamadec-${MACHINE}"
EXTRA_OECONF_append_pn-tuxtxt-enigma2 = " --with-fbdev=/dev/fb2"
CC_append_pn-showiframe = " -DHAVE_AMLOGIC -I${STAGING_INCDIR}/amlogic/amcodec -lamcodec"
DEPENDS_append_pn-showiframe = " wetek-libamcodec-${MACHINE}"

MACHINE_EXTRA_RRECOMMENDS = " \
    wetek-libamplayer-${MACHINE} \
    wetek-libamavutils-${MACHINE} \
    wetek-libamadec-${MACHINE} \
    wetek-libamcodec-${MACHINE} \
    "

IMAGE_FSTYPES += "ext4 sdcard"
IMAGE_ROOTFS_SIZE = "976562"
IMAGE_EXTRA_SPACE = "0"
IMAGE_OVERHEAD_FACTOR = "1.0"

# Serial console to be used
SERIAL_CONSOLE = "115200 ttyS0"

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

UBOOT_ENTRYPOINT  = "0x80008000"
UBOOT_LOADADDRESS = "0x80008000"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CLASSES += "image_types_wetek"

IMAGE_CMD_sdcard_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.wetek-sd.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${IMAGE_NAME}.sdcard.img; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_sdcard.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -f ${DEPLOY_DIR_IMAGE}/.wetek-sd.img; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.rootfs.wetek-sd.img; \
    rm -Rf ${IMAGEDIR}; \
    "

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

BBMASK = "meta-vuplus/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"

