# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "xtrend"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'ET11000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'U4' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'Galaxy Innovations' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'Beyonwiz' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'et1x000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'beyonwiz/u4' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'gi11000', 'et1x000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'beyonwizu4' , '', d)}\
"

MTD_ROOTFS = "mmcblk0p4"
MTD_KERNEL = "mmcblk0p3"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.tar.bz2"

IMAGEDIR ?= "${MACHINE}"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    et-dvb-modules-${MACHINE_DRIVER} \
    et-initrd-${MACHINE} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix --with-eac3 --with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
IMAGE_FSTYPES =+ "tar.bz2"

PREFERRED_PROVIDER_virtual/kernel = "linux-etxx00"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "et-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "et-blindscan-utils-${MACHINE}"

MACHINE_FEATURES += "alsa 3dtv switchoff hdmicec omb  \
    ${@bb.utils.contains('MACHINEBUILD', 'beyonwizu4', 'dvbc-only' , 'blindscan-tbs', d)} \
"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
