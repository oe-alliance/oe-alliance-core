include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc
require conf/machine/include/arm/armv7a/tune-cortexa15.inc
# We know about rtc
include conf/machine/include/build-extras.inc

PACKAGECONFIG_GL:pn-qtbase = " "
PACKAGECONFIG:append:pn-qtbase = " gles2 linuxfb"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff blindscan-tbs HDMI qthbbtv qtstalker openhbbtv hbbtv-browser-webkit qtevent1 hisichromium"

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 --with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark"

PREFERRED_PROVIDER_virtual/egl ?= "abcom-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "abcom-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "abcom-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgbm ?= "abcom-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"
PREFERRED_PROVIDER_virtual/blindscan-dvbc ?= "abcom-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs ?= "abcom-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/kernel = "linux-abcom"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

RCTYPE = "30"
RCNAME = "abcom"
RCHARDWARE = "V2"

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    abcom-libs-${HICHIPSET} \
    abcom-fastboot-${MACHINE} \
    abcom-param-${MACHINE} \
    abcom-bootlogo-${MACHINE} \
    abcom-bootargs-${MACHINE} \
    abcom-recovery-${MACHINE} \
    abcom-tntfs-${HICHIPSET} \
    abcom-dvb-modules-${MACHINE} \
    kernel-module-mali-${HICHIPSET} \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cdfs \
    abcom-ethwol \
    resizerootfs \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    ffmpeg \
    gstreamer1.0-libav \
    "

CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-abcom = "4.4.35"

IMAGEDIRRECOVERY ?= "${MACHINE}-recovery"

IMAGE_CMD:abfastboot8gb:append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}; \
    cp ${IMGDEPLOYDIR}/bootoptions.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootoptions.img; \
    cp ${IMGDEPLOYDIR}/misc-boot.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/misc-boot.img; \
    cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/fastboot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/bootargs.bin; \
    cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/pqparam.bin; \
    cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/baseparam.img; \
    if [ -e ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/${MACHINE}/recovery.bin ]; then \
        cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/${MACHINE}/recovery.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/recovery.bin; \
    fi; \
    if [ -e ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ]; then \
        cp ${DEPLOY_DIR_IMAGE}/logo-${DISTRO_NAME}.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
    else \
        cp ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINE}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/logo.img; \
    fi; \
    echo "${MACHINEBUILD}-${DATE}-RECOVERY" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/${IMAGEDIR}/recoveryversion; \
    echo "***** ACHTUNG *****" >${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Das RECOVERY wird nur benöetigt wenn es Probleme gibt beim zugriff auf das MULTIBOOT MENÜ" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Das ${IMAGE_NAME}_recovery_usb.zip sollte normalerweise einmal installiert werden (oder wenn es ein Update gibt.)" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Dies ist erforderlich, um Probleme mit dem Images zuvermeiden,wenn sich der Aufbau der Partition ändert (bootargs)." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "Die Änderungen können alle Daten im Flash Löschen, nur Installieren wenn es notwendig ist." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_lies.mich; \
    echo "***** WARNING *****" >${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "The RECOVERY is only needed if there are problems accessing the MULTIBOOT MENU" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "The ${IMAGE_NAME}_recovery_usb.zip should normally be installed only once (or if there is an update.)" >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "This is necessary to avoid problems with the image if the structure of the partition changes (bootargs)." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
    echo "The changes will delete all data in the flash, only install it, if it is necessary." >>${DEPLOY_DIR_IMAGE}/${IMAGEDIRRECOVERY}/recovery_${MACHINEBUILD}_READ.ME; \
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
    echo "When you enter the recovery menu then it will then force the ${IMAGE_NAME}_usb.zip into the linux1 slot" >> ${DEPLOY_DIR_IMAGE}/${MACHINE}/${IMAGEDIR}/force_${MACHINE}_READ.ME; \
    cd ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    zip -r ../${IMAGE_NAME}_mmc.zip *; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    cd ..; \
    rm -Rf ${DEPLOY_DIR_IMAGE}/${MACHINE}; \
    "
