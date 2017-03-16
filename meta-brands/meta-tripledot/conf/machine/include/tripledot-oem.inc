# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "tripledot"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'enibox', 'EniBox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mago', 'MAGO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tyrant', 'tyrant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xcombo', 'enfinityX combo plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x1plus', 'enfinityX 1plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf108', 'SF108 E2HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 't2cable', 'ENFINITY T2/CABLE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf98', 'SF98' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evopanda', 'SLIM panda' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'enibox', 'hdbox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mago', 'relook' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tyrant', 'tripledot' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xcombo', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x1plus', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf108', 'Octagon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 't2cable', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf98', 'Octagon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evopanda', 'EVO' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'enibox', 'enibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mago', 'mago' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tyrant', 'tyrant' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xcombo', 'xcombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x1plus', 'x1plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf108', 'sf108' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 't2cable', 't2cable' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf98', 'sf98' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evopanda', 'slim' , '', d)}\
"

MTD_KERNEL ?= "\
${@bb.utils.contains('MACHINEBUILD', 'enibox', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mago', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tyrant', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xcombo', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x1plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf108', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 't2cable', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf98', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evopanda', 'mtd2' , '', d)}\
"

MTD_ROOTFS ?= "\
${@bb.utils.contains('MACHINEBUILD', 'enibox', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mago', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'tyrant', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xcombo', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x1plus', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf108', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 't2cable', 'mtd8' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf98', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evopanda', 'mtd0' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    tripledot-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-tripledot"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/cfe.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/splash.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/imageversion.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/kernel.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/rootfs.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/finished.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    ${@bb.utils.contains('MACHINE', 'yh7362', \
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


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash omb feature1 feature2 blindscan-tbs"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
