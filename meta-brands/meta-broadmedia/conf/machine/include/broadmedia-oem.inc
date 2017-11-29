# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "broadmedia"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'mbtwinplus', 'Premium Twin Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf3038', 'SF3038' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf128', 'SF128' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf138', 'SF138' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2zet2c', 'BRE2ZE T2/C' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'alphatriple', 'Alpha Triple HD' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'mbtwinplus', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf3038', 'Octagon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf128', 'Octagon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf138', 'Octagon' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2zet2c', 'WWIO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'alphatriple', 'SAB' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'mbtwinplus', 'miraclebox/twinplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf3038', 'sf3038' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf128', 'sf128' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf138', 'sf138' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2zet2c', 'bre2ze_t2c' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'alphatriple', 'sab_alpha_triple' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'mbtwinplus', 'twinplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf3038', 'sf3038' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf128', 'g100' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf138', 'g100' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bre2zet2c', 'bre2zet2c' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'alphatriple', 'alphatriple' , '', d)}\
"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    broadmedia-dvb-modules-${MACHINE_DRIVER} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 \
    ${@bb.utils.contains('MACHINE', 'g100', ' --with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'g101', ' --with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-broadmedia"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "broadmedia-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "broadmedia-blindscan-utils"

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
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
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

MACHINE_FEATURES += "alsa 3dtv switchoff hdmicec bootsplash omb feature1 feature2 blindscan-tbs"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/soc-family.inc

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"