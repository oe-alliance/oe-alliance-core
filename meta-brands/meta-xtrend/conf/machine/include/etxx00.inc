# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "xtrend"
MACHINE_BRAND = "Xtrend"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    et-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d-nxpl', 'xtrend-v3ddriver-${MACHINE}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'oem-info', 'oem-info', '',d)} \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 --with-max-pcmrate-48K \
    ${@bb.utils.contains('MACHINE', 'et8500', '--with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'et7x00', '--with-vb6 --with-vb8 --with-spark --with-h265' , '', d)} \
"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/egl ?= "xtrend-v3ddriver-${MACHINE}" 
PREFERRED_PROVIDER_virtual/libgles1 ?= "xtrend-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "xtrend-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgbm ?= "xtrend-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"

PREFERRED_PROVIDER_virtual/kernel = "linux-etxx00"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "et-blindscan-dvbs-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "et-blindscan-dvbc-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGEDIR ?= "${MACHINE}"

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
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash omb feature1"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/mips/tune-mips32.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

EXTRA_IMAGEDEPENDS += " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "

MACHINE_EXTRA_RDEPENDS_${PN} = " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
