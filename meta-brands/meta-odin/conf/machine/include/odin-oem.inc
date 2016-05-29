# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "odin"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'classm', 'CLASS M' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodin', 'AX ODIN' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodinc', 'AX ODIN-C' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'starsatlx', 'LX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'genius', 'genius' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evo', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxym6', 'GalaxyM6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'geniuse3hd', 'Genius E3HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoe3hd', 'E3HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3', 'E3HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3c', 'E3HD-C' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maram9', 'M9' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'classm', 'Axas' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodin', 'Opticum' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodinc', 'Opticum' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'starsatlx', 'Starsat' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'genius', 'Galaxy Innovations' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evo', 'Optibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxym6', 'T-rex' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'geniuse3hd', 'Galaxy Innovations' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoe3hd', 'Optibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3', 'Axas' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3c', 'Axas' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maram9', 'ODIN' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'classm', 'en2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodin', 'en2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodinc', 'en2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'starsatlx', 'en2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'genius', 'en2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evo', 'en2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxym6', 'en2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'geniuse3hd', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoe3hd', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3c', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maram9', 'odinm9' , '', d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'classm', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodin', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodinc', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'starsatlx', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'genius', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evo', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxym6', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'geniuse3hd', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoe3hd', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3c', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maram9', 'mtd2' , '', d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'classm', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodin', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodinc', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'starsatlx', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'genius', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evo', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxym6', 'mtd4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'geniuse3hd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoe3hd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3c', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maram9', 'mtd3' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'classm', 'odinm7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodin', 'odinm7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axodinc', 'odinm7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'starsatlx', 'odinm7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'genius', 'odinm7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evo', 'odinm7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'galaxym6', 'odinm7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'geniuse3hd', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoe3hd', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'axase3c', 'e3hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maram9', 'odinm9' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    odin-dvb-modules-${MACHINE_DRIVER} \
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

IMAGE_CMD_ubi_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \  
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash blindscan-dvbs blindscan-dvbc omb boxmodel feature1 feature2 \
    ${@bb.utils.contains('MACHINEBUILD', 'axodinc', 'dvb-c dvbc-only' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'axase3c', 'dvb-c dvbc-only' , '', d)} \
    "

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"


