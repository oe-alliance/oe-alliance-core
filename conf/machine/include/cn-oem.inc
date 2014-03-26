# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "cn"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'sogno8800hd', '8800HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'uniboxhde', 'HDECO' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'sogno8800hd', 'Sogno' , '', d)}\
${@base_contains('MACHINEBUILD', 'uniboxhde', 'UNiBOX' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'sogno8800hd', 'sogno/8800hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'uniboxhde', 'unibox/hde' , '', d)}\
"

MTD_ROOTFS = "mtd7"
MTD_KERNEL = "mtd8"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

PREFERRED_PROVIDER_virtual/kernel = "linux-cn"
PREFERRED_VERSION_linux-cn = "3.9.7"
PREFERRED_VERSION_linux-libc-headers = "3.9.7"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "
	
UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -f -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${IMAGE_VERSION}-${MACHINE}-${DATE}_usb > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "
	
MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi 3dtvpli bootsplash"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"