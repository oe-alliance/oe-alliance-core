# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "ini"

GST_VERSION = "1.0"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'ventonhdx', 'HD-x' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam1000hd', '1000-HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam5000hd', '5000-HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezammarvel', 'Marvel' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx', 'LX-x' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx3', 'LX-3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoslim', 'Slim' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmini', 'Premium Mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbminiplus', 'Premium Mini Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbhybrid', 'Premium Hybrid' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbtwin', 'Premium Twin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbultra', 'Premium Ultra' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio5x00', '5x00-HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6000', '6000-HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6100', '6100-HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6200', '6200-HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', 'Nemesis' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt2', 'T2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt3', 'T3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt4', 'T4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bwidowx', 'Black Widow-X' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'opticumtt', 'Twin' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'ventonhdx', 'UNiBOX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam1000hd', 'Sezam' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam5000hd', 'Sezam' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezammarvel', 'Sezam' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx', 'GI' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx3', 'GI' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoslim', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmini', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbminiplus', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbhybrid', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbtwin', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbultra', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio5x00', 'Atemio' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6000', 'Atemio' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6100', 'Atemio' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6200', 'Atemio' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', 'Atemio' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt2', 'Beyonwiz' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt3', 'Beyonwiz' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt4', 'Beyonwiz' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bwidowx', 'SPACE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'opticumtt', 'Opticum' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'ventonhdx', 'venton-hdx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam1000hd', 'hde' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam5000hd', 'hdx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezammarvel', 'hdp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx', 'xpeedlx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx3', 'xpeedlx3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoslim', 'xpeedlxpro' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmini', 'miraclebox/mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbminiplus', 'miraclebox/miniplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbhybrid', 'miraclebox/minihybrid' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbtwin', 'miraclebox/twin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbultra', 'miraclebox/ultra' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio5x00', 'atemio/5x00' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6000', 'atemio/6000' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6100', 'atemio/6100' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6200', 'atemio/6200' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', 'atemio/8x00' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt2', 'beyonwiz/hde2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt3', 'beyonwiz/hdx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt4', 'beyonwiz/hdp' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bwidowx', 'bwidowx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'opticumtt', 'opticum/tt' , '', d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'ventonhdx', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam1000hd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam5000hd', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezammarvel', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx3', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoslim', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmini', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbminiplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbhybrid', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbtwin', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbultra', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio5x00', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6000', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6100', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6200', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt2', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt3', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt4', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bwidowx', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'opticumtt', 'mtd2' , '', d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'ventonhdx', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam1000hd', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam5000hd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezammarvel', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx3', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoslim', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmini', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbminiplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbhybrid', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbtwin', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbultra', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio5x00', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6000', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6100', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6200', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt2', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt3', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt4', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bwidowx', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'opticumtt', 'mtd0' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'ventonhdx', 'inihdx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam1000hd', 'inihde' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezam5000hd', 'inihdx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sezammarvel', 'ini-900' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx', 'inihde' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'xpeedlx3', 'ini-900' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'evoslim', 'yhgd5034' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmini', 'inihde' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbminiplus', 'inihde2sv' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbhybrid', 'inihde2sv' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbtwin', 'inihdx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbultra', 'ini-800' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio5x00', 'inihde' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6000', 'inihde2am' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6100', 'inihde2am' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemio6200', 'inihde2am' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'atemionemesis', 'ini-800' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt2', 'inihde2au' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt3', 'inihdx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'beyonwizt4', 'ini-840t4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bwidowx', 'yhgd2580-7358' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'opticumtt', 'ini-435oc' , '', d)}\
"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
	ini-dvb-modules-${MACHINE_DRIVER} \
	"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
    "

include conf/machine/include/autoload-filesystems.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"
DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts"

PREFERRED_VERSION_nfs-utils ?= "1.2.8"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "ini-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "ini-blindscan-utils"

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
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/cfe.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/splash.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/imageversion.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/kernel.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/rootfs.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "extrasplash", "cp ${DEPLOY_DIR_IMAGE}/finished.bmp ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/;" , "", d)} \
    mv ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    gzip -f -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${IMAGE_VER_STRING} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    ${@bb.utils.contains('MACHINEBUILD', 'evoslim', \
    'echo "dont change this file" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/force;' , \
    'echo "rename this file to force to force an update without confirmation" > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/noforce;', d)} \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubi; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.ubifs; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -rf ${IMAGEDIR}; \
    rm -rf atemio; \
    rm -rf miraclebox; \
    rm -rf beyonwiz; \
    rm -rf bwidowx; \
    rm -rf bwidowx2; \
    rm -rf opticum; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash omb feature1 feature2"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
