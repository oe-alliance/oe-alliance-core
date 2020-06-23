PACKAGECONFIG_GL_pn-qtbase = " "
PACKAGECONFIG_append_pn-qtbase += " gles2 linuxfb"

MACHINE_FEATURES += "qthbbtv qtstalker"

PREFERRED_PROVIDER_virtual/egl ?= "gfutures-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "gfutures-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "gfutures-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgbm ?= "gfutures-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"
PREFERRED_PROVIDER_virtual/blindscan-dvbc ?= "gfutures-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs ?= "gfutures-dvb-modules-${MACHINE}"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil', 'gfutures-libs-${HICHIPSET} gfutures-partitions-${MACHINE} gfutures-bootargs-${MACHINE} gfutures-recovery-${MACHINE} gfutures-tntfs-${HICHIPSET}' , '', d)} \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'gfutures-mali-${HICHIPSET} kernel-module-mali-${HICHIPSET}' , '', d)} \
    "

CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-gfutures = "4.4.35"

IMAGEDIRRECOVERY ?= "${MACHINE}-recovery"

IMAGE_CMD_hdfastboot8gb_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}; \
    cp ${IMGDEPLOYDIR}/bootoptions.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootoptions.img; \
    cp ${IMGDEPLOYDIR}/misc-boot.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/misc-boot.img; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/pqparam.bin; \
    cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/recovery.bin ]; then \
        cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/recovery.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/recovery.bin; \
    fi; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
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
require conf/machine/include/gfutures-oem.inc
