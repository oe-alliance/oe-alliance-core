require conf/machine/include/gigablue-oem.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"
KERNEL_IMAGETYPE = "vmlinux"


MACHINE_FEATURES += " feature1 feature2"

DVBMEDIASINK_CONFIG = "--with-wmv \
    ${@bb.utils.contains('MACHINE', 'gb7356', '--with-wma --with-pcm --with-dts --with-eac3 --with-amr' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb7358', '--with-wma --with-pcm --with-dts --with-eac3 --with-amr' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb7362', '--with-wma --with-pcm --with-dts --with-eac3 --with-amr' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb73625', '--with-wma --with-pcm --with-dts --with-eac3 --with-amr --with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
"

GLIBC_EXTRA_OECONF = "--with-tls \
    ${@bb.utils.contains('MACHINE', 'gb7358', '--with-__thread' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb7356', '--with-__thread' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb7362', '--with-__thread' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb73625', '--with-__thread' , '', d)} \
"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "cp ${DEPLOY_DIR_IMAGE}/lcdsplash220.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdsplash.bin;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "cp ${DEPLOY_DIR_IMAGE}/lcdwarning220.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdwarning.bin;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd220", "cp ${DEPLOY_DIR_IMAGE}/lcdwaitkey220.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdwaitkey.bin;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "cp ${DEPLOY_DIR_IMAGE}/lcdsplash400.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdsplash.bin;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "cp ${DEPLOY_DIR_IMAGE}/lcdwarning400.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdwarning.bin;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "gigabluelcd400", "cp ${DEPLOY_DIR_IMAGE}/lcdwaitkey400.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdwaitkey.bin;" , "", d)} \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    cp ${DEPLOY_DIR_IMAGE}/warning.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/warning.bin; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -f -c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip -r ${IMAGE_NAME}_usb.zip gigablue/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -rf ${DEPLOY_DIR_IMAGE}/gigablue; \
    "

IMAGE_CMD_jffs2_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_jffs2_append = "; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.jffs2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -f -c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip -r ${IMAGE_NAME}_usb.zip gigablue/*; \
    zip ${IMAGE_NAME}_usb.zip burn.bat; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.jffs2; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.jffs2; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -rf ${DEPLOY_DIR_IMAGE}/gigablue; \
    "

require conf/machine/include/tune-mips32.inc
