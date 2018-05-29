require conf/machine/include/soc-family.inc
include conf/machine/include/autoload-filesystems.inc

# We know about rtc
include conf/machine/include/build-extras.inc

PREFERRED_PROVIDER_virtual/libgles1 = "odroid-opengl-amlogic"
PREFERRED_PROVIDER_virtual/libgles2 = "odroid-opengl-amlogic"
PREFERRED_PROVIDER_virtual/egl = "odroid-opengl-amlogic"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"

EXTRA_OECONF_append_pn-enigma2 = " --with-alsa --with-alsacard=hw:0 --with-alsamixer=Master --with-fbdev=/dev/fb0 --with-lcddev=/dev/null --with-amlogic"
DEPENDS_append_pn-enigma2 = " libdvbcsa odroid-libamcodec-${MACHINE} odroid-libamadec-${MACHINE}"
EXTRA_OECONF_append_pn-tuxtxt-enigma2 = " --with-fbdev=/dev/fb0"
CC_append_pn-showiframe = " -DHAVE_AMLOGIC -I${STAGING_INCDIR}/amlogic/amcodec -lamcodec"
DEPENDS_append_pn-showiframe = " odroid-libamcodec-${MACHINE}"


MACHINE_EXTRA_RRECOMMENDS = " \
    odroid-libamplayer-${MACHINE} \
    odroid-libamavutils-${MACHINE} \
    odroid-libamadec-${MACHINE} \
    odroid-libamcodec-${MACHINE} \
    "

# Serial console to be used
SERIAL_CONSOLE = "115200 ttyS0"

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CLASSES += "image_types_odroid"

SDCARD_ROOTFS ?= "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext4"
IMAGE_FSTYPES ?= "ext4 sdcard"

IMAGE_CMD_sdcard_append  = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${SDIMG} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${SDIMG}.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.rootfs.sdcard.img; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

BBMASK .= "meta-vuplus/recipes-webkit/"
BBMASK += "meta-vuplus/recipes-webkit/"

