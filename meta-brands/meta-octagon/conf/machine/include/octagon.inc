PACKAGECONFIG_GL:pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "octagon"
MACHINE_BRAND = "Octagon"

IMAGEDIR ?= "${MACHINE}"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    octagon-dvb-modules-${MACHINE} \
    octagon-initrd-${MACHINE} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cdfs \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d', 'nextv-v3ddriver-${MACHINE}', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d', 'platform-util-${MACHINE}', '',d)} \
    ${@bb.utils.contains_any('MACHINE_FEATURES', 'kodi22', 'mkswapdev-${MACHINE}', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'oem-info', 'oem-info', '',d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts=no --with-dtsdownmix --with-eac3 --with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
IMAGE_FSTYPES =+ "tar.bz2"

PREFERRED_PROVIDER_virtual/kernel = "linux-octagon"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "octagon-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "octagon-blindscan-utils"

PREFERRED_PROVIDER_virtual/egl = "nextv-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "nextv-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

MACHINE_FEATURES += "alsa 3dtv switchoff hdmicec omb blindscan-tbs blindscan-dvbc HDMI"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/arm/armv7a/tune-cortexa15.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
