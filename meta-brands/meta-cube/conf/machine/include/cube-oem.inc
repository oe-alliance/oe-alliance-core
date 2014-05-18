# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "cube"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'dcube', 'R2' , '', d)}\
${@base_contains('MACHINEBUILD', 'ultima', 'Ultima' , '', d)}\
${@base_contains('MACHINEBUILD', 'mkcube', 'Cube' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'dcube', 'D-Cube' , '', d)}\
${@base_contains('MACHINEBUILD', 'ultima', 'Stratos' , '', d)}\
${@base_contains('MACHINEBUILD', 'mkcube', 'MK-Digital' , '', d)}\
"

IMAGEDIR = "\
${@base_contains('MACHINEBUILD', 'dcube', 'flash-image-256' , '', d)}\
${@base_contains('MACHINEBUILD', 'ultima', 'flash-image-256' , '', d)}\
${@base_contains('MACHINEBUILD', 'mkcube', 'flash-image-256' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    e2bmc-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
    mtd-utils-native \
    "
	
include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix --with-eac3"

# image name, used for multi boot selection
MULTIBOOT_IMAGE_NAME ?= "${IMAGE_BASENAME}"

# a rootfs size of 192MB should be sufficient (can be overridden by an image)
ROOTFS_SIZE ?= "C000000"

UBI_VOLNAME = "rootfs"

# image name in the zipfile, can be overridden by an image
ZIPFILE_IMAGE_NAME ?= "firmware.img"

EXTRA_IMAGEDEPENDS += "createimage-${MACHINE}"

IMAGE_CMD_imgzip256 = " \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_256.ubifs -m 2048 -e 126976 -c 4096; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    createimage_${MACHINE} 126976 ${MULTIBOOT_IMAGE_NAME} kernel ${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin 0 0 rootfs ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_256.ubifs ${ROOTFS_SIZE} 1 > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ZIPFILE_IMAGE_NAME}; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}-256MB.zip ${IMAGEDIR}/*; \
    rm -Rf ${IMAGEDIR}; \
    "

IMAGE_CMD_imgzip1024 = " \
    mkfs.ubifs -r ${IMAGE_ROOTFS} -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_1024.ubifs -m 4096 -e 253952 -c 4096; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/flash-image-1024; \
    createimage_${MACHINE} 253952 ${MULTIBOOT_IMAGE_NAME} kernel ${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin 0 0 rootfs ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs_1024.ubifs ${ROOTFS_SIZE} 1 > ${DEPLOY_DIR_IMAGE}/flash-image-1024/${ZIPFILE_IMAGE_NAME}; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}-1024MB.zip flash-image-1024/*; \
    rm -Rf flash-image-1024; \
    "

IMAGE_DEVICE_TABLES = ""

PREFERRED_VERSION_wpa-supplicant = "0.5.8"
PREFERRED_VERSION_rtl8192cu = "3.3.0"
PREFERRED_VERSION_nfs-utils ?= "1.2.8"

PREFERRED_PROVIDER_virtual/kernel = "linux-cube"
PREFERRED_PROVIDER_virtual/egl = "opengl"

PREFERRED_VERSION_linux-cube = "2.6.34"
PREFERRED_VERSION_linux-libc-headers = "3.1"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

TARGET_ARCH = "arm"
TUNE_FEATURES = "cortexa9 vfp"
include conf/machine/include/tune-cortexa9.inc
TUNE_CCARGS += "-mfpu=vfpv3-d16"

IMAGE_FSTYPES ?= "imgzip256"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff textlcd wifi dvbapi5 usbhost wifiusblegacy legacykernel"

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"
