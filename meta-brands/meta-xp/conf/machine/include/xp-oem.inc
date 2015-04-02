# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "xp"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'xp1000mk', 'XP1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'xp1000max', 'XP1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf8', 'SF8' , '', d)}\
${@base_contains('MACHINEBUILD', 'xp1000plus', 'XP1000 + ' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'xp1000mk', 'MK-Digital' , '', d)}\
${@base_contains('MACHINEBUILD', 'xp1000max', 'MaxDigital' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf8', 'Octagon' , '', d)}\
${@base_contains('MACHINEBUILD', 'xp1000plus', 'Octagon' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'xp1000mk', 'xp1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'xp1000max', 'xp1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf8', 'xp1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'xp1000plus', 'xp1000' , '', d)}\
"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    xp-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-xp"
PREFERRED_VERSION_linux-xp = "3.12.1"
PREFERRED_VERSION_linux-libc-headers = "3.12.1"

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
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
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

MACHINE_FEATURES += "alsa wifi 3dtv switchoff hdmicec bootsplash omb"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

EXTRA_IMAGEDEPENDS += " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "

MACHINE_EXTRA_RDEPENDS_${PN} = " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"