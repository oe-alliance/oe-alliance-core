# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "entwopia"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'enfinity', 'ENfinity' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'marvel1', 'Marvel 1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x2plus', 'enfinityX 2plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2ze', 'BRE2ZE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evomini', 'ENfinity mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evominiplus', 'ENfinity mini+' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipert2c', 'Viper T2/C' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombo', 'Viper Combo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombohdd', 'Viper Combo HDD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viperslim', 'Viper SLIM' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'enfinity', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'marvel1', 'VisionNet' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x2plus', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2ze', 'WWIO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evomini', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evominiplus', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipert2c', 'AMIKO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombo', 'AMIKO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombohdd', 'AMIKO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viperslim', 'AMIKO' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'enfinity', 'enfinity' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'marvel1', 'marvel1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x2plus', 'x2plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2ze', 'bre2ze' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evomini', 'mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evominiplus', 'miniplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipert2c', 'vipert2c' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombo', 'vipercombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombohdd', 'vipercombohdd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viperslim', 'viperslim' , '', d)}\
"

MTD_KERNEL ?= "\
${@bb.utils.contains('MACHINEBUILD', 'enfinity', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'marvel1', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x2plus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2ze', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evomini', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evominiplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipert2c', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombo', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombohdd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viperslim', 'mtd2' , '', d)}\
"

MTD_ROOTFS ?= "\
${@bb.utils.contains('MACHINEBUILD', 'enfinity', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'marvel1', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'x2plus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2ze', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evomini', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evominiplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipert2c', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombo', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vipercombohdd', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'viperslim', 'mtd0' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    entwopia-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 \
    ${@bb.utils.contains('MACHINE', 'ch625lc', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'yh625tc', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'yh625dt', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'ch625dt', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'yh73625', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

PREFERRED_PROVIDER_virtual/kernel = "linux-entwopia"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

FORCE ?= "no"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel.bin; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    if [ "${FORCE}" = "yes" ]; then \
        echo "rename this file to 'noforce' to force an update with confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/force; \
    else \
        echo "rename this file to 'force' to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce; \
    fi; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf ${IMAGEDIR}; \
    "


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash omb skins1080 feature1 feature2 blindscan-tbs "

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/soc-family.inc

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
