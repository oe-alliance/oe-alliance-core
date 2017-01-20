# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "ceryon"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'twinboxlcd', 'TWINBOX LCD' , '', d)}\
${@base_contains('MACHINEBUILD', 'twinboxlcdci5', 'TWINBOX LCD' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicro', 'Premium Micro' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicrov2', 'Premium Micro 2' , '', d)}\
${@base_contains('MACHINEBUILD', 'odin2hybrid', 'ODIN2 Hybrid' , '', d)}\
${@base_contains('MACHINEBUILD', 'odinplus', 'ODIN2 Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hd', 'E4HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdhybrid', 'E4HD HYBRID' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdcombo', 'E4HD COMBO' , '', d)}\
${@base_contains('MACHINEBUILD', 'singleboxlcd', 'SINGLEBOX LCD' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf208', 'SF208 E2 HD LCD SINGLE' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf228', 'SF228 E2 HD LCD TWIN' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf238', 'SF228 E2 HD LCD TWIN' , '', d)}\
${@base_contains('MACHINEBUILD', '9910lx', '9910LX' , '', d)}\
${@base_contains('MACHINEBUILD', '9911lx', '9911LX' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'twinboxlcd', 'RED EAGLE' , '', d)}\
${@base_contains('MACHINEBUILD', 'twinboxlcdci5', 'RED EAGLE' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicro', 'Miraclebox' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicrov2', 'Miraclebox' , '', d)}\
${@base_contains('MACHINEBUILD', 'odin2hybrid', 'Opticum' , '', d)}\
${@base_contains('MACHINEBUILD', 'odinplus', 'Opticum' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hd', 'AXAS' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdhybrid', 'AXAS' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdcombo', 'AXAS' , '', d)}\
${@base_contains('MACHINEBUILD', 'singleboxlcd', 'RED EAGLE' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf208', 'OCTAGON' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf228', 'OCTAGON' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf238', 'OCTAGON' , '', d)}\
${@base_contains('MACHINEBUILD', '9910lx', 'Protek' , '', d)}\
${@base_contains('MACHINEBUILD', '9911lx', 'Protek' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'twinboxlcd', 'redeagle/twinboxlcd' , '', d)}\
${@base_contains('MACHINEBUILD', 'twinboxlcdci5', 'redeagle/twinboxlcd' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicro', 'miraclebox/micro' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicrov2', 'miraclebox/microv2' , '', d)}\
${@base_contains('MACHINEBUILD', 'odin2hybrid', 'odin2/hybrid' , '', d)}\
${@base_contains('MACHINEBUILD', 'odinplus', 'odinplus' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hd', 'e4hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdhybrid', 'e4hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdcombo', 'e4hdcombo' , '', d)}\
${@base_contains('MACHINEBUILD', 'singleboxlcd', 'redeagle/singleboxlcd' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf208', 'sf208' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf228', 'sf228' , '', d)}\
${@base_contains('MACHINEBUILD', 'sf238', 'sf228' , '', d)}\
${@base_contains('MACHINEBUILD', '9910lx', '9910lx' , '', d)}\
${@base_contains('MACHINEBUILD', '9911lx', '9911lx' , '', d)}\
"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    ceryon-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    ceryon-shutdown \
    ${@base_contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 \
    ${@base_contains('MACHINE', '7005s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', '7105s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', '7215s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', '7225s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "ceryon-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "ceryon-blindscan-utils"

PREFERRED_PROVIDER_virtual/kernel = "linux-ceryon"

PREFERRED_VERSION_linux-ceryon = "4.0.1"
PREFERRED_VERSION_linux-libc-headers = "4.0"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 4096"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@base_contains("MACHINE_FEATURES", "lcd220", "cp ${DEPLOY_DIR_IMAGE}/lcdsplash220.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdsplash.bin;" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "lcd220", "cp ${DEPLOY_DIR_IMAGE}/lcdwarning220.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdwarning.bin;" , "", d)} \
    ${@base_contains("MACHINE_FEATURES", "lcd220", "cp ${DEPLOY_DIR_IMAGE}/lcdwaitkey220.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/lcdwaitkey.bin;" , "", d)} \
    cp ${DEPLOY_DIR_IMAGE}/splash.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/splash.bin; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
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

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash omb videoenhancement directfb skins1080 feature1 feature2 blindscan-dvbs blindscan-dvbc blindscan-tbs \
    ${@base_contains('MACHINEBUILD', 'e4hdhybrid', 'dvb-c dvb-c-only' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'e4hdcombo', 'dvb-c' , '', d)} \
    ${@base_contains('MACHINEBUILD', '9911lx', 'dvb-c' , '', d)} \
"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc
require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
