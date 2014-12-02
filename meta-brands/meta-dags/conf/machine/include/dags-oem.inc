# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dags"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'TM-2T-OE' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'TM-NANO-OE' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'TM-NANO-2T' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'TM-NANO-3T' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'TM-NANO 2 Super' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'TM-Single' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'TM-Twin-OE' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'IOS-100HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'IOS-200HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'IOS-300HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'IOS-300HDV2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'Force 1' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'Force 1 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2solid', 'force2 solid' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'force1' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'force1 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'Roxxs 200HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'MediaArt 200HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'Optimuss OS1 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'Optimuss OS2 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'Optimuss OS3 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'Optimuss OS1' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'Optimuss OS2' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'MediaBox HD LX' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'Technomate' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2solid', 'Iqon' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'WorldVision' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'WorldVision' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'Roxxs' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'MediaArt' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'Jepssen' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'tm2t', 'update/tm2toe/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'update/tmnanooe/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'update/tmnano2t/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'update/force1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'update/tmnano2super/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'update/tmsingle/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'update/tmtwinoe/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'update/ios100/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'update/ios200/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'update/ios300/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'update/ios300new/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'update/force1/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'update/force1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2solid', 'update/force2solid/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'update/force1/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'update/force1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'update/ios200/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'update/ios200/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'update/optimussos1plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'update/optimussos2plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'update/optimussos3plus/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'update/optimussos1/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'update/optimussos2/cfe' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'update/mediabox/cfe' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2solid', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'mtd1' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'mtd6' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINEBUILD', 'tm2t', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2t', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmsingle', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmtwin', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios100hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios200hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'iqonios300hdv2', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2solid', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'roxxs200hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediaart200hd', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2', 'mtd4' , '', d)}\
${@base_contains('MACHINEBUILD', 'mediabox', 'mtd4' , '', d)}\
"

SPLASH1_BMP = "\
${@base_contains('MACHINEBUILD', 'tmnano2super', 'splash1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'tmnano3t', 'splash1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force1plus', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'force2solid', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'worldvisionf1plus', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos1plus', 'splash1_os1.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos2plus', 'splash1_os2.bmp' , '', d)}\
${@base_contains('MACHINEBUILD', 'optimussos3plus', 'splash1_os2.bmp' , '', d)}\
"

KERNEL_FILE = "oe_kernel.bin"
ROOTFS_FILE = "oe_rootfs.bin"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    dags-shutdown \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"
DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts"

PREFERRED_PROVIDER_virtual/kernel = "linux-dags"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@base_contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    ${@base_contains("MACHINE", "dags3", "cp ${DEPLOY_DIR_IMAGE}/${SPLASH1_BMP} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash1.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags3", "cp ${DEPLOY_DIR_IMAGE}/splash2.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash2.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags3", "cp ${DEPLOY_DIR_IMAGE}/splash3.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash3.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags4", "cp ${DEPLOY_DIR_IMAGE}/${SPLASH1_BMP} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash1.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags4", "cp ${DEPLOY_DIR_IMAGE}/splash2.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash2.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags4", "cp ${DEPLOY_DIR_IMAGE}/splash3.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash3.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags5", "cp ${DEPLOY_DIR_IMAGE}/${SPLASH1_BMP} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash1.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags5", "cp ${DEPLOY_DIR_IMAGE}/splash2.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash2.bmp;" , "", d)} \
    ${@base_contains("MACHINE", "dags5", "cp ${DEPLOY_DIR_IMAGE}/splash3.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash3.bmp;" , "", d)} \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -f -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}-${MACHINEBUILD}-${DATE} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf update; \
    "


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash textlcd fan ci omb skins1080"

BOOTLOGO_FILENAME = "splash.bmp"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"
