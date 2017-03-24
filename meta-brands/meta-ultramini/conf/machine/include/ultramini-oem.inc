# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "ultramini"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'XPEED LX CLASS S2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'XPEED LX CLASS C' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'et7x00mini', 'ET-7000 Mini' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'GOLDEN INTERSTAR' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'GOLDEN INTERSTAR' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'et7x00mini', 'Galaxy Innovations' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'xpeedc' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'xpeedc' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'et7x00mini', 'et7x00' , '', d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'et7x00mini', 'mtd1' , '', d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'et7x00mini', 'mtd2' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'xpeedc' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'xpeedc' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'et7x00mini', 'et7000mini' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    ultramini-dvb-modules-${MACHINE_DRIVER} \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix \
    ${@bb.utils.contains('MACHINE', 'ultramini', '--with-vb6 --with-vb8 --with-spark' , '', d)} \
"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-ultramini"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "ultramini-blindscan-utils-dvbc"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "ultramini-blindscan-utils-dvbs"

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
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
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

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash omb skins1080 feature1 feature2 \
    ${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcc', 'dvb-c dvbc-only blindscan-dvbc boxmodel' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'xpeedlxcs2', 'boxmodel' , '', d)} \
    "

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
