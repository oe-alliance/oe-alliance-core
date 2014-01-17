# We know about rtc
include conf/machine/include/build-extras.inc

MACHINE_OEM = "odin"
MACHINE_BRAND = "Odin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    odin-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-odin"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "odin-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "odin-blindscan-utils-${MACHINE}"

PREFERRED_VERSION_linux-odin = "3.6.0"
PREFERRED_VERSION_linux-libc-headers = "3.6"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_m7 = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    rm -rf ${IMAGE_ROOTFS}/etc/.machine; \
    rm -rf ${IMAGE_ROOTFS}/etc/.brand; \
    rm -rf ${IMAGE_ROOTFS}/etc/hostname; \
    echo ${MACHINE} > ${IMAGE_ROOTFS}/etc/hostname; \
    echo \[ubifs\] > ubinize.cfg; \
    echo mode=ubi >> ubinize.cfg; \
    echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
    echo vol_id=0 >> ubinize.cfg; \
    echo vol_type=dynamic >> ubinize.cfg; \
    echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
    echo vol_flags=autoresize >> ubinize.cfg; \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs ${MKUBIFS_ARGS} && ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} for Odin M7 > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "

IMAGE_CMD_evo = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    rm -rf ${IMAGE_ROOTFS}/etc/.machine; \
    rm -rf ${IMAGE_ROOTFS}/etc/.brand; \
    rm -rf ${IMAGE_ROOTFS}/etc/hostname; \
    echo EVO > ${IMAGE_ROOTFS}/etc/hostname; \
    echo EVO > ${IMAGE_ROOTFS}/etc/.machine; \
    echo Optibox > ${IMAGE_ROOTFS}/etc/.brand; \
    echo \[ubifs\] > ubinize.cfg; \
    echo mode=ubi >> ubinize.cfg; \
    echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
    echo vol_id=0 >> ubinize.cfg; \
    echo vol_type=dynamic >> ubinize.cfg; \
    echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
    echo vol_flags=autoresize >> ubinize.cfg; \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs ${MKUBIFS_ARGS} && ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} for Optibox EVO > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${DISTRO_NAME}-${IMAGE_VERSION}-evo-${DATE}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "
	
IMAGE_CMD_genius = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    rm -rf ${IMAGE_ROOTFS}/etc/.machine; \
    rm -rf ${IMAGE_ROOTFS}/etc/.brand; \
    rm -rf ${IMAGE_ROOTFS}/etc/hostname; \
    echo Genius > ${IMAGE_ROOTFS}/etc/hostname; \
    echo Genius > ${IMAGE_ROOTFS}/etc/.machine; \
    echo Galaxy Innovations > ${IMAGE_ROOTFS}/etc/.brand; \
    echo \[ubifs\] > ubinize.cfg; \
    echo mode=ubi >> ubinize.cfg; \
    echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
    echo vol_id=0 >> ubinize.cfg; \
    echo vol_type=dynamic >> ubinize.cfg; \
    echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
    echo vol_flags=autoresize >> ubinize.cfg; \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs ${MKUBIFS_ARGS} && ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} for Galaxy Innovations Genius > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${DISTRO_NAME}-${IMAGE_VERSION}-genius-${DATE}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "
	
IMAGE_CMD_starsatlx = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    rm -rf ${IMAGE_ROOTFS}/etc/.machine; \
    rm -rf ${IMAGE_ROOTFS}/etc/.brand; \
    rm -rf ${IMAGE_ROOTFS}/etc/hostname; \
    echo LX > ${IMAGE_ROOTFS}/etc/hostname; \
    echo LX > ${IMAGE_ROOTFS}/etc/.machine; \
    echo Telesat > ${IMAGE_ROOTFS}/etc/.brand; \
    echo \[ubifs\] > ubinize.cfg; \
    echo mode=ubi >> ubinize.cfg; \
    echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
    echo vol_id=0 >> ubinize.cfg; \
    echo vol_type=dynamic >> ubinize.cfg; \
    echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
    echo vol_flags=autoresize >> ubinize.cfg; \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs ${MKUBIFS_ARGS} && ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} for Telesat LX > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${DISTRO_NAME}-${IMAGE_VERSION}-starsat-lx-${DATE}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "
	
IMAGE_CMD_ax-odin = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    rm -rf ${IMAGE_ROOTFS}/etc/.machine; \
    rm -rf ${IMAGE_ROOTFS}/etc/.brand; \
    rm -rf ${IMAGE_ROOTFS}/etc/hostname; \
    echo ax-odin > ${IMAGE_ROOTFS}/etc/hostname; \
    echo ax-odin > ${IMAGE_ROOTFS}/etc/.machine; \
    echo Opticum > ${IMAGE_ROOTFS}/etc/.brand; \
    echo \[ubifs\] > ubinize.cfg; \
    echo mode=ubi >> ubinize.cfg; \
    echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
    echo vol_id=0 >> ubinize.cfg; \
    echo vol_type=dynamic >> ubinize.cfg; \
    echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
    echo vol_flags=autoresize >> ubinize.cfg; \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs ${MKUBIFS_ARGS} && ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} for Opticum ax-odin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${DISTRO_NAME}-${IMAGE_VERSION}-ax-odin-${DATE}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "
	
IMAGE_CMD_classm = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    rm -rf ${IMAGE_ROOTFS}/etc/.machine; \
    rm -rf ${IMAGE_ROOTFS}/etc/.brand; \
    rm -rf ${IMAGE_ROOTFS}/etc/hostname; \
    echo Classm > ${IMAGE_ROOTFS}/etc/hostname; \
    echo Classm > ${IMAGE_ROOTFS}/etc/.machine; \
    echo Axas > ${IMAGE_ROOTFS}/etc/.brand; \
    echo \[ubifs\] > ubinize.cfg; \
    echo mode=ubi >> ubinize.cfg; \
    echo image=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs >> ubinize.cfg; \
    echo vol_id=0 >> ubinize.cfg; \
    echo vol_type=dynamic >> ubinize.cfg; \
    echo vol_name=${UBI_VOLNAME} >> ubinize.cfg; \
    echo vol_flags=autoresize >> ubinize.cfg; \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubifs ${MKUBIFS_ARGS} && ubinize -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${UBINIZE_ARGS} ubinize.cfg; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} for Axas Classm > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${DISTRO_NAME}-${IMAGE_VERSION}-classm-${DATE}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "	

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash boxmodel"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

