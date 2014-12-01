# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "odin"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'classm', 'CLASS M' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodin', 'AX ODIN' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodinc', 'AX ODIN-C' , '', d)}\
${@base_contains('MACHINEBUILD', 'starsatlx', 'LX' , '', d)}\
${@base_contains('MACHINEBUILD', 'genius', 'genius' , '', d)}\
${@base_contains('MACHINEBUILD', 'evo', 'EVO' , '', d)}\
${@base_contains('MACHINEBUILD', 'galaxym6', 'GalaxyM6' , '', d)}\
${@base_contains('MACHINEBUILD', 'geniuse3hd', 'Genius E3HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'evoe3hd', 'E3HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3', 'E3HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3c', 'E3HD-C' , '', d)}\
${@base_contains('MACHINEBUILD', 'maram9', 'M9' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'classm', 'Axas' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodin', 'Opticum' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodinc', 'Opticum' , '', d)}\
${@base_contains('MACHINEBUILD', 'starsatlx', 'Starsat' , '', d)}\
${@base_contains('MACHINEBUILD', 'genius', 'Galaxy Innovations' , '', d)}\
${@base_contains('MACHINEBUILD', 'evo', 'Optibox' , '', d)}\
${@base_contains('MACHINEBUILD', 'galaxym6', 'T-rex' , '', d)}\
${@base_contains('MACHINEBUILD', 'geniuse3hd', 'Galaxy Innovations' , '', d)}\
${@base_contains('MACHINEBUILD', 'evoe3hd', 'Optibox' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3', 'Axas' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3c', 'Axas' , '', d)}\
${@base_contains('MACHINEBUILD', 'maram9', 'ODIN' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'classm', 'en2' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodin', 'en2' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodinc', 'en2' , '', d)}\
${@base_contains('MACHINEBUILD', 'starsatlx', 'en2' , '', d)}\
${@base_contains('MACHINEBUILD', 'genius', 'en2' , '', d)}\
${@base_contains('MACHINEBUILD', 'evo', 'en2' , '', d)}\
${@base_contains('MACHINEBUILD', 'galaxym6', 'en2' , '', d)}\
${@base_contains('MACHINEBUILD', 'geniuse3hd', 'e3hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'evoe3hd', 'e3hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3', 'e3hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3c', 'e3hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'maram9', 'odinm9' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINEBUILD', 'classm', 'mtd3' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodin', 'mtd3' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodinc', 'mtd3' , '', d)}\
${@base_contains('MACHINEBUILD', 'starsatlx', 'mtd3' , '', d)}\
${@base_contains('MACHINEBUILD', 'genius', 'mtd3' , '', d)}\
${@base_contains('MACHINEBUILD', 'evo', 'mtd3' , '', d)}\
${@base_contains('MACHINEBUILD', 'galaxym6', 'mtd3' , '', d)}\
${@base_contains('MACHINEBUILD', 'geniuse3hd', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'evoe3hd', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3c', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'maram9', 'mtd2' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINEBUILD', 'classm', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodin', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'axodinc', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'starsatlx', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'genius', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'evo', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'galaxym6', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'geniuse3hd', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'evoe3hd', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'axase3c', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'maram9', 'mtd3' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    odin-dvb-modules-${MACHINE} \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-odin"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "odin-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "odin-blindscan-utils-${MACHINE}"

PREFERRED_VERSION_linux-odin = "3.6.0"
PREFERRED_VERSION_linux-libc-headers = "3.6"
PREFERRED_VERSION_nfs-utils ?= "1.2.8"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@base_contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash blindscan-dvbs blindscan-dvbc omb \
    ${@base_contains('MACHINEBUILD', 'axodinc', 'dvbc-only' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'axase3c', 'dvbc-only' , '', d)} \	
    "

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"


