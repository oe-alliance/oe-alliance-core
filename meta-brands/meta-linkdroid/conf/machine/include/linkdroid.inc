MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " ${@bb.utils.contains('MACHINE_FEATURES', 'fb', 'linkdroid-amlsetfb', '', d)}"

PREFERRED_PROVIDER_virtual/libgles1 = "linkdroid-opengl-amlogic"
PREFERRED_PROVIDER_virtual/libgles2 = "linkdroid-opengl-amlogic"
PREFERRED_PROVIDER_virtual/egl = "linkdroid-opengl-amlogic"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"

EXTRA_OECONF_append_pn-enigma2 = " --with-alsa --with-alsacard=hw:0 --with-alsamixer=Master --with-fbdev=/dev/fb0 --with-lcddev=/dev/null --with-amlogic"
DEPENDS_append_pn-enigma2 = " libdvbcsa linkdroid-libamcodec-${MACHINE} linkdroid-libamadec-${MACHINE} "
EXTRA_OECONF_append_pn-tuxtxt-enigma2 = " --with-fbdev=/dev/fb0"
CC_append_pn-showiframe = " -DHAVE_AMLOGIC -I${STAGING_INCDIR}/amlogic/amcodec -lamcodec -lgstpbutils-1.0 -lgstreamer-1.0 -lgobject-2.0 -lglib-2.0"
DEPENDS_append_pn-showiframe = " linkdroid-libamcodec-${MACHINE} gstreamer1.0 gstreamer1.0-libav  gstreamer1.0-plugins-base  gstreamer1.0-plugins-good gstreamer1.0-plugins-ugly"

DISTRO_FEATURES_remove = "x11 wayland"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CMD_alien5sdimg_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-sd.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${IMAGE_NAME}-sd.img; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_sdcard.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -f ${DEPLOY_DIR_IMAGE}/*-sd.img; \
    rm -Rf ${IMAGEDIR}; \
    "

BBMASK += "meta-vuplus/recipes-graphics/"
BBMASK += "meta-fulan/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
