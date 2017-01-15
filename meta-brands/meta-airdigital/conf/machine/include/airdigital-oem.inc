# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "airdigital"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'zgemmash1', 'Star H1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmash2', 'Star H2' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmas2s', 'Star 2S' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmass', 'Star S' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmahs', 'H.S' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2s', 'H.2S' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2h', 'H.2H' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmaslc', 'Star LC' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah4', 'H4' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5', 'H5' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah52s', 'H5.2S' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmai55', 'I55' , '', d)}\
${@base_contains('MACHINEBUILD', 'novacombo', 'NOVA COMBO' , '', d)}\
${@base_contains('MACHINEBUILD', 'novaip', 'NOVA IP' , '', d)}\
${@base_contains('MACHINEBUILD', 'novatwin', 'NOVA TWIN' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah3ac', 'H3 AC' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5ac', 'H5 AC' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'zgemmash1', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmash2', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmas2s', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmass', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmahs', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2s', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2h', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmaslc', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah4', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah52s', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmai55', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'novacombo', 'EVO' , '', d)}\
${@base_contains('MACHINEBUILD', 'novaip', 'EVO' , '', d)}\
${@base_contains('MACHINEBUILD', 'novatwin', 'EVO' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah3ac', 'Zgemma' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5ac', 'Zgemma' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'zgemmash1', 'zgemma/sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmash2', 'zgemma/sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmas2s', 'zgemma/sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmass', 'zgemma/sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmahs', 'zgemma/h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2s', 'zgemma/h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2h', 'zgemma/h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmaslc', 'zgemma/lc' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah4', 'zgemma/h4' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5', 'zgemma/h5' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah52s', 'zgemma/h5' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmai55', 'zgemma/i55' , '', d)}\
${@base_contains('MACHINEBUILD', 'novacombo', 'novacombo' , '', d)}\
${@base_contains('MACHINEBUILD', 'novaip', 'novaip' , '', d)}\
${@base_contains('MACHINEBUILD', 'novatwin', 'novatwin' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah3ac', 'zgemma/h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5ac', 'zgemma/h5' , '', d)}\
"

MACHINE_DRIVER = "\
${@base_contains('MACHINEBUILD', 'zgemmash1', 'sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmash2', 'sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmas2s', 'sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmass', 'sh1' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmahs', 'h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2s', 'h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah2h', 'h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmaslc', 'lc' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah4', 'h4' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5', 'h5' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah52s', 'h5' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmai55', 'i55' , '', d)}\
${@base_contains('MACHINEBUILD', 'novacombo', 'novacombo' , '', d)}\
${@base_contains('MACHINEBUILD', 'novaip', 'novaip' , '', d)}\
${@base_contains('MACHINEBUILD', 'novatwin', 'novatwin' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah3ac', 'h3' , '', d)}\
${@base_contains('MACHINEBUILD', 'zgemmah5ac', 'h5' , '', d)}\
"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    airdigital-dvb-modules-${MACHINE_DRIVER} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 \
    ${@base_contains('MACHINE', 'h5', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', 'h6', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    "

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-airdigital"

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
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    ${@base_contains('MACHINE', 'i55', \
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


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash omb boxmodel feature1 blindscan-tbs"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

