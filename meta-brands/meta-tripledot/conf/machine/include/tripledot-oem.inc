# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "tripledot"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'enibox', 'EniBox' , '', d)}\
${@base_contains('MACHINEBUILD', 'mago', 'MAGO' , '', d)}\
${@base_contains('MACHINEBUILD', 'tyrant', 'tyrant' , '', d)}\
${@base_contains('MACHINEBUILD', 'xcombo', 'enfinityX combo plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'x1plus', 'enfinityX 1plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf108', 'SF108 E2HD' , '', d)}\
${@base_contains('MACHINEBUILD', 't2cable', 'ENFINITY T2/CABLE' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf98', 'SF98' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'enibox', 'hdbox' , '', d)}\
${@base_contains('MACHINEBUILD', 'mago', 'relook' , '', d)}\
${@base_contains('MACHINEBUILD', 'tyrant', 'tripledot' , '', d)}\
${@base_contains('MACHINEBUILD', 'xcombo', 'EVO' , '', d)}\
${@base_contains('MACHINEBUILD', 'x1plus', 'EVO' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf108', 'Octagon' , '', d)}\
${@base_contains('MACHINEBUILD', 't2cable', 'EVO' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf98', 'Octagon' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'enibox', 'enibox' , '', d)}\
${@base_contains('MACHINEBUILD', 'mago', 'mago' , '', d)}\
${@base_contains('MACHINEBUILD', 'tyrant', 'tyrant' , '', d)}\
${@base_contains('MACHINEBUILD', 'xcombo', 'xcombo' , '', d)}\
${@base_contains('MACHINEBUILD', 'x1plus', 'x1plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf108', 'sf108' , '', d)}\
${@base_contains('MACHINEBUILD', 't2cable', 't2cable' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf98', 'sf98' , '', d)}\
"

MTD_KERNEL ?= "\
${@base_contains('MACHINEBUILD', 'enibox', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'mago', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tyrant', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'xcombo', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'x1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf108', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 't2cable', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf98', 'mtd2' , '', d)}\
"

MTD_ROOTFS ?= "\
${@base_contains('MACHINEBUILD', 'enibox', 'mtd8' , '', d)}\
${@base_contains('MACHINEBUILD', 'mago', 'mtd8' , '', d)}\
${@base_contains('MACHINEBUILD', 'tyrant', 'mtd8' , '', d)}\
${@base_contains('MACHINEBUILD', 'xcombo', 'mtd8' , '', d)}\
${@base_contains('MACHINEBUILD', 'x1plus', 'mtd8' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf108', 'mtd8' , '', d)}\
${@base_contains('MACHINEBUILD', 't2cable', 'mtd8' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf98', 'mtd0' , '', d)}\
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
    ${@base_contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/cfe.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/splash.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/imageversion.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/kernel.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/rootfs.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/finished.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    ${@base_contains('MACHINE', 'yh7362', \
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
