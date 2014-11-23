# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "ini"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'HD-x' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', '1000-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', '5000-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezammarvel', 'Marvel' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'LX-x' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'LX-3' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'Premium Mini' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'Premium Twin' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbultra', 'Premium Ultra' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio5x00', '5x00-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6000', '6000-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6100', '6100-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6200', '6200-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemionemesis', 'Nemesis' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt3', 'T3' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt4', 'T4' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'UNiBOX' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', 'Sezam' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', 'Sezam' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezammarvel', 'Sezam' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'GI' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'GI' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'Miraclebox' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'Miraclebox' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbultra', 'Miraclebox' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio5x00', 'Atemio' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6000', 'Atemio' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6100', 'Atemio' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6200', 'Atemio' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemionemesis', 'Atemio' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt3', 'Beyonwiz' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt4', 'Beyonwiz' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'venton-hdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', 'hde' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', 'hdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezammarvel', 'hdp' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'xpeedlx' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'xpeedlx3' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'miraclebox/mini' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'miraclebox/twin' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbultra', 'miraclebox/ultra' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio5x00', 'atemio/5x00' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6000', 'atemio/6000' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6100', 'atemio/6100' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6200', 'atemio/6200' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemionemesis', 'atemio/8x00' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt3', 'beyonwiz/hdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt4', 'beyonwiz/hdp' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezammarvel', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbultra', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio5x00', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6000', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6100', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6200', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemionemesis', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt3', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt4', 'mtd2' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezammarvel', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbultra', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio5x00', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6000', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6100', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6200', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemionemesis', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt3', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt4', 'mtd0' , '', d)}\
"

MACHINE_DRIVER = "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'inihdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', 'inihde' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', 'inihdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezammarvel', 'ini-900' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'inihde' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'ini-900' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'inihde' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'inihdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbultra', 'ini-800' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio5x00', 'inihde' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6000', 'inihde2am' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6100', 'inihde2am' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemio6200', 'inihde2am' , '', d)}\
${@base_contains('MACHINEBUILD', 'atemionemesis', 'ini-800' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt3', 'inihdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'beyonwizt4', 'ini-840t4' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
	ini-dvb-modules-${MACHINE_DRIVER} \
	"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"
DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts"

PREFERRED_VERSION_nfs-utils ?= "1.2.8"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "ini-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "ini-blindscan-utils"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@base_contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -f -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${DISTRO_NAME}-${IMAGE_VERSION}-${MACHINEBUILD}-${DATE} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -rf ${IMAGEDIR}; \
    rm -rf atemio; \
    rm -rf miraclebox; \
    rm -rf beyonwiz; \ 
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash omb"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"
