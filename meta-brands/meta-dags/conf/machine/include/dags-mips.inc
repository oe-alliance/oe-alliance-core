include conf/machine/include/dags-oem.inc

PACKAGECONFIG_GL:pn-qtbase = "gles2 eglfs linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"

KERNEL_FILE = "oe_kernel.bin"
ROOTFS_FILE = "oe_rootfs.bin"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

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
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7356", "cp ${DEPLOY_DIR_IMAGE}/${SPLASH1_BMP} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash1.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7356", "cp ${DEPLOY_DIR_IMAGE}/splash2.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash2.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7356", "cp ${DEPLOY_DIR_IMAGE}/splash3.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash3.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7362", "cp ${DEPLOY_DIR_IMAGE}/${SPLASH1_BMP} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash1.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7362", "cp ${DEPLOY_DIR_IMAGE}/splash2.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash2.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags7362", "cp ${DEPLOY_DIR_IMAGE}/splash3.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash3.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags73625", "cp ${DEPLOY_DIR_IMAGE}/${SPLASH1_BMP} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash1.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags73625", "cp ${DEPLOY_DIR_IMAGE}/splash2.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash2.bmp;" , "", d)} \
    ${@bb.utils.contains("MACHINE", "dags73625", "cp ${DEPLOY_DIR_IMAGE}/splash3.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash3.bmp;" , "", d)} \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -f -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf update; \
    "

require conf/machine/include/mips/tune-mips32.inc

PREFERRED_PROVIDER_virtual/egl = "v3d-libgles-${MACHINE}" 
PREFERRED_PROVIDER_virtual/libgles2 = "v3d-libgles-${MACHINE}"

