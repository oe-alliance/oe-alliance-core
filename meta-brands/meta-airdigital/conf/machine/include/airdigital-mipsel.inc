PACKAGECONFIG_GL:pn-qtbase = " "
PACKAGECONFIG:append:pn-qtbase = " gles2 linuxfb"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_VERSION_linux-airdigital = "4.10.12"

PREFERRED_PROVIDER_virtual/egl ?= "airdigital-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "airdigital-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "airdigital-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgbm ?= "airdigital-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"

IMAGE_FSTYPES ?= "ubi"

IMAGE_CMD:ubifs:prepend = " \
	mkdir -p ${IMAGE_ROOTFS}/usr/lib; \
	if [ "${PACKAGE_LIST}" = "1" ]; then \
        cp ${IMAGE_MANIFEST} ${IMAGE_ROOTFS}/usr/lib/package.lst; \
    fi; \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD:ubi:append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    ${@bb.utils.contains('MACHINE', 'i55', \
    'echo "dont change this file" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/force;' , \
    'echo "rename this file to force to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce;', d)} \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "

MACHINE_FEATURES += " bootsplash omb boxmodel feature1 linuxwifi skins1080"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/mips/tune-mips32.inc
require conf/machine/include/airdigital-oem.inc
