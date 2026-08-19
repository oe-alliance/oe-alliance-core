PACKAGECONFIG_GL:pn-qtbase ?= "gles2 eglfs linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

# We know about rtc
include conf/machine/include/build-extras.inc

RCNAME = "vu6"
MACHINE_NAME = "Duo4kLite"
BRAND_OEM = "vuplus"
MACHINE_BRAND = "Vu+"
RCHARDWARE = "VU"
IMAGEDIR ?= "vuplus/duo4klite"
MTD_KERNEL = "mmcblk0p4"
MTD_ROOTFS = "mmcblk0p13"
MULTIBOOT_STARTUP_DEFAULT ?= "STARTUP_1"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " \
    vuplus-platform-util-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    kernel-module-cdfs \
    kernel-module-zram \
    kernel-module-ext2 \
    kernel-module-ext3 \
    kernel-module-ext4 \
    kernel-module-cifs \
    kernel-module-autofs4 \
    firmware-aic8800d80 \
    vuplus-aic8800-driver \
    resizerootfs \
    vuplus-swapcreate \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    vuplus-initrd-cfe-${MACHINE} \
    "

include conf/machine/include/autoload-filesystems.inc

PREFERRED_PROVIDER_virtual/transtreamproxy = "vuplus-transtreamproxy"

PREFERRED_PROVIDER_virtual/kernel = "linux-vuplus"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash skins1080 blindscan-tbs HDMI HDMI-IN-FHD"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
IMAGE_FSTYPES =+ "tar.bz2"

require conf/machine/include/arm/armv7a/tune-cortexa15.inc

VIRTUAL-RUNTIME_blindscan_dvbc = "vuplus-blindscan-dvbc-utils"
VIRTUAL-RUNTIME_blindscan_dvbs = "vuplus-blindscan-dvbs-utils"
PREFERRED_PROVIDER_virtual/egl = "vuplus-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/libgles2 = "vuplus-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/chromium-browser = "chromium-browser-${MACHINE}"

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-amr --with-pcm --with-dts --with-eac3 --with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark"

MACHINE_FEATURES += " omb mmc emmc swap"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

IMAGE_CMD:tar:prepend = "\
	mkdir -p ${IMAGE_ROOTFS}/usr/lib; \
	if [ "${PACKAGE_LIST}" = "1" ]; then \
        cp ${IMAGE_MANIFEST} ${IMAGE_ROOTFS}/usr/lib/package.lst; \
    fi; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    ${@bb.utils.contains("MACHINE_FEATURES", "bootsplash", "cp ${DEPLOY_DIR_IMAGE}/${BOOTLOGO_FILENAME} ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${BOOTLOGO_FILENAME};" , "", d)} \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}_boot.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/boot.bin; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}_rescue.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rescue.bin; \
    cp ${DEPLOY_DIR_IMAGE}/${MACHINE}_initrd.bin ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/initrd.bin; \
    cp ${DEPLOY_DIR_IMAGE}/zImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    echo ${DISTRO_NAME}-${DISTRO_VERSION}.${BUILD_VERSION} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    touch ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${MULTIBOOT_STARTUP_DEFAULT}; \
    cd ${IMAGE_ROOTFS}; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
    mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    bzip2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/rootfs.tar; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.rootfs.tar.bz2; \
    rm -f ${DEPLOY_DIR_IMAGE}/.tar.bz2; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -Rf vuplus; \
"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
