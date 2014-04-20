# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "ixuss"
MACHINE_BRAND = "IXUSS"
MTD_ROOTFS = "mtd2"
MTD_KERNEL = "mtd1"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
    ixuss-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    kernel-module-ext2 \
    kernel-module-ext3 \
    kernel-module-ext4 \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    ixuss-shutdown \
    gst-plugin-dvbmediasink \
    "

module_autoload_ext2 = "ext2"
module_autoload_ext3 = "ext3"
module_autoload_ext4 = "ext4"

include conf/machine/include/autoload-filesystems.inc

CHIPSET = "bcm7413"

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

PREFERRED_PROVIDER_virtual/kernel = "linux-ixuss"

PREFERRED_VERSION_linux-ixuss = "3.3.0"
PREFERRED_VERSION_linux-libc-headers = "3.3"
PREFERRED_VERSION_nfs-utils ?= "1.2.8"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/cfe-bootlogo.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/cfe-bootlogo.bmp; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -rf ${IMAGEDIR}; \
    "


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff textlcd wifi bootsplash boxmodel"

BOOTLOGO_FILENAME = "cfe-bootlogo.bmp"

require conf/machine/include/tune-mips32.inc
