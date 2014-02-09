# We know about rtc
include conf/machine/include/build-extras.inc

MACHINE_OEM = "ebox"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'mixosf5', 'F5' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixosf5mini', 'F5 mini' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixosf7', 'F7' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixoslumi', 'Lumi' , '', d)}\
${@base_contains('MACHINEBUILD', 'gi9196m', '9196m' , '', d)}\
${@base_contains('MACHINEBUILD', 'gi9196lite', '9196 Lite' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'mixosf5', 'MixOS' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixosf5mini', 'MixOS' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixosf7', 'MixOS' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixoslumi', 'MixOS' , '', d)}\
${@base_contains('MACHINEBUILD', 'gi9196m', 'Galaxy Innovations' , '', d)}\
${@base_contains('MACHINEBUILD', 'gi9196lite', 'Galaxy Innovations' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'mixosf5', 'ebox/7403' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixosf5mini', 'ebox/7403' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixosf7', 'ebox/7358' , '', d)}\
${@base_contains('MACHINEBUILD', 'mixoslumi', 'ebox/7403' , '', d)}\
${@base_contains('MACHINEBUILD', 'gi9196m', 'ebox/7403' , '', d)}\
${@base_contains('MACHINEBUILD', 'gi9196lite', 'ebox/7403' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    ebox-dvb-modules-${MACHINE} \
    ebox-shutdown \
    "
    
MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"
DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts"

PREFERRED_PROVIDER_virtual/kernel = "linux-ebox"
PREFERRED_VERSION_linux-ebox = "3.12.4"
PREFERRED_VERSION_linux-libc-headers = "3.12.4"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "null"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c 1024 -F"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_ubi_append = " \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ubi ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/root_cfe_auto.bin; \
    gzip -9c ${DEPLOY_DIR_IMAGE}/vmlinux-${MACHINE}.bin > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/kernel_cfe_auto.bin; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ubifs; \
    rm -Rf ${IMAGEDIR}; \
    "

MACHINE_FEATURES += "alsa wifi dvbapi5 usbhost switchoff wifi 3dtv hdmicec boxmodel"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

