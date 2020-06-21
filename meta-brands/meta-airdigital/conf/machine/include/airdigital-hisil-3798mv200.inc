PACKAGECONFIG_GL_pn-qtbase = " "
PACKAGECONFIG_append_pn-qtbase += " gles2 linuxfb"

MACHINE_FEATURES += "qthbbtv qtstalker"

PREFERRED_PROVIDER_virtual/egl ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libopencl ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgbm ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "airdigital-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "airdigital-dvb-modules-${MACHINE}"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-${HICHIPSET}', 'airdigital-bootlogo-${HICHIPSET} airdigital-libs-${HICHIPSET} airdigital-param-${HICHIPSET} airdigital-bootargs-${HICHIPSET} airdigital-fastboot-${HICHIPSET} airdigital-tntfs-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisi-recovery', 'airdigital-recovery-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'SDcard', 'airdigital-bootargs-extra-${MACHINE}' , '', d)} \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'airdigital-mali-${HICHIPSET} kernel-module-mali-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'bluetooth', 'kernel-module-btusb bluez5' , '', d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-3798mv200', 'ffmpeg gstreamer1.0-libav' , 'gstreamer1.0-libav ', d)} \
    "

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

MKUBIFS_ARGS = "-m 2048 -e 126976 -c 8192"
UBINIZE_ARGS = "-m 2048 -p 128KiB"
UBI_VOLNAME = "rootfs"
CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-airdigital = "4.4.35"

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.ubi ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/${KERNEL_FILE}; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.tar; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/update_bootargs_${MACHINE}.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/pq_param.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${IMAGE_NAME}" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    zip -r ../${IMAGE_NAME}_usb.zip *; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    cd ..; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}; \
    "

IMAGEDIRRECOVERY ?= "${MACHINE}-recovery"

IMAGE_CMD_zgemmafastboot8gb_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}; \
    cp ${IMGDEPLOYDIR}/bootoptions.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootoptions.img; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/pq_param.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/recovery.bin ]; then \
        cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/recovery.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/recovery.bin; \
    fi; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/zgemma-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${MACHINEBUILD}-${DATE}-RECOVERY" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/recoveryversion; \
    echo "***** ACHTUNG *****" >${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Das RECOVERY wird nur benöetigt wenn es Probleme gibt beim zugriff auf das MULTIBOOT MENÜ" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Das ${IMAGE_NAME}_recovery_usb.zip sollte normalerweise einmal installiert werden (oder wenn es ein Update gibt.)" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Dies ist erforderlich, um Probleme mit dem Images zuvermeiden,wenn sich der Aufbau der Partition ändert (bootargs)." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Die Änderungen können alle Daten im Flash Löschen, nur Installieren wenn es notwendig ist." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "***** ATTENTION *****" >${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "This RECOVERY only need when you have issue access the MULTIBOOT MENU" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "The ${IMAGE_NAME}_recovery_usb.zip should been install normally once (or if there is an update.)" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "This is needed for avoid images conflicts as the partition layout (bootargs) is of huge essential." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "A small change can destroy all your install images. So we can better leave it and not install it if it's not need." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    cd ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY} ; \
    zip -r ../${IMAGE_NAME}_recovery_emmc.zip *; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/uImage; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/rootfs.tar; \
    echo "${IMAGE_NAME}" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/imageversion; \
    echo "${IMAGE_NAME}_usb.zip" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/unforce_${MACHINE}.txt; \
    echo "Rename the unforce_${MACHINE}.txt to force_${MACHINE}.txt and move it to the root of your usb-stick" > ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/force_${MACHINE}_READ.ME; \
    echo "When you enter the recovery menu then it will force to install the image ${IMAGE_NAME}_usb.zip in the linux1 selection" >> ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/force_${MACHINE}_READ.ME; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    zip -r ../${IMAGE_NAME}_mmc.zip *; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    cd ..; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    "

require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/airdigital-oem.inc
