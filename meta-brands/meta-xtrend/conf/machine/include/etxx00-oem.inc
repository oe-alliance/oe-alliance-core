PACKAGECONFIG_GL_pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB_pn-qtbase = " "
# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "xtrend"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'ET11000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4k51', 'VIPER4K51' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'U4' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'Galaxy Innovations' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4k51', 'AMIKO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'Beyonwiz' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'et1x000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4k51', 'et1x000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'beyonwiz/u4' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'et1x000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viper4k51', 'et1x000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'beyonwizu4' , '', d)}\
"

MTD_ROOTFS = "mmcblk0p4"
MTD_KERNEL = "mmcblk0p3"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"

IMAGEDIR ?= "${MACHINE}"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    et-dvb-modules-${MACHINE_DRIVER} \
    et-initrd-${MACHINE} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cdfs \
	${@bb.utils.contains('MACHINE_FEATURES', 'v3d', 'et-v3ddriver-${MACHINE}', '',d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'v3d', 'platform-util-${MACHINE}', '',d)} \
	${@bb.utils.contains('MACHINE_FEATURES', 'kodi19', 'mkswapdev-${MACHINE}', '',d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix --with-eac3 --with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
IMAGE_FSTYPES =+ "tar.bz2"

PREFERRED_PROVIDER_virtual/kernel = "linux-etxx00"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "et-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "et-blindscan-utils-${MACHINE}"

PREFERRED_PROVIDER_virtual/egl = "et-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "et-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

MACHINE_FEATURES += "alsa 3dtv switchoff hdmicec omb  \
    ${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'dvbc-only' , 'blindscan-tbs', d)} \
"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
