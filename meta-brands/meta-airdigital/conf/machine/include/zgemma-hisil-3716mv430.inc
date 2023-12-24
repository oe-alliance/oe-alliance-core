PACKAGECONFIG_GL:pn-qtbase = "no-opengl linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

MACHINE_FEATURES += "qthbbtv qtstalker"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-${HICHIPSET}', 'airdigital-bootlogo-${HICHIPSET} airdigital-libs-${HICHIPSET} airdigital-param-${HICHIPSET} airdigital-bootargs-${HICHIPSET} airdigital-fastboot-${HICHIPSET} airdigital-tntfs-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisi-recovery', 'airdigital-recovery-${HICHIPSET}' , '', d)} \
    "

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa7hf"

MKUBIFS_ARGS = "-m 4096 -e 253952 -c 2048"
UBINIZE_ARGS = "-m 4096 -p 256KiB -s 4096"
UBI_VOLNAME = "rootfs"
IMAGEDIRRECOVERY ?= "zgemma-recovery"
CMDLINE ?= "console=ttyAMA0,115200n8"

PREFERRED_VERSION_linux-airdigital = "4.4.176"

IMAGE_CMD:ubifs:prepend = " \
	mkdir -p ${IMAGE_ROOTFS}/usr/lib; \
	if [ "${PACKAGE_LIST}" = "1" ]; then \
        cp ${IMAGE_MANIFEST} ${IMAGE_ROOTFS}/usr/lib/package.lst; \
    fi; \
	rm -Rf ${IMAGE_ROOTFS}/boot/*; \
	rm -Rf ${IMAGE_ROOTFS}/tmp/*; \
	"

IMAGE_CMD:ubi:append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}; \
    cp ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/${ROOTFS_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/${KERNEL_FILE}; \
    cp ${IMGDEPLOYDIR}/bootoptions.yaffs ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootoptions.yaffs; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/update_bootargs_${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/update_bootargs_${MACHINE}.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/pqparam.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/recovery.bin ]; then \
        cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/recovery.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/recovery.bin; \
    fi; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${IMAGE_NAME}" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}; \
    zip -r ../${IMAGE_NAME}_recovery_usb.zip *; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    cd ..; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}; \
    cp ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/${KERNEL_FILE}; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    zip -r ../${IMAGE_NAME}_nand.zip *; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    "

require conf/machine/include/arm/armv7a/tune-cortexa7.inc
require conf/machine/include/airdigital-oem.inc