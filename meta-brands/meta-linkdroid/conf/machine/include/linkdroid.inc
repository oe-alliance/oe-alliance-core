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
CC_append_pn-showiframe = " -DHAVE_AMLOGIC -I${STAGING_INCDIR}/amlogic/amcodec -lamcodec"
DEPENDS_append_pn-showiframe = " linkdroid-libamcodec-${MACHINE}"

DISTRO_FEATURES_remove = "x11 wayland"

BBMASK += "meta-vuplus/recipes-graphics/"
BBMASK += "meta-fulan/recipes-graphics/"
BBMASK += "meta-vuplus/recipes-webkit/"
