# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "vuplus"
MACHINE_BRAND = "Vu+"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
    ${@base_contains("MACHINE_FEATURES", "dvbproxy", "vuplus-dvb-proxy-${MACHINE} vuplus-libgles-${MACHINE} vuplus-platform-util-${MACHINE}", "vuplus-dvb-modules-${MACHINE}", d)} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    vuplus-shutdown \
    gst-plugin-dvbmediasink \
    gst-ffmpeg \
    ${@base_contains('DISTRO_FEATURES', 'audioeffect', 'enigma2-plugin-systemplugins-audioeffect' , '', d)} \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-amr --with-wmv --with-pcm --with-eac3"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_VERSION_nfs-utils ?= "1.2.8"

PREFERRED_PROVIDER_virtual/kernel = "linux-vuplus"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/transtreamproxy = "vuplus-transtreamproxy"
PREFERRED_PROVIDER_virtual/xmbc = "vuplus-xbmc"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c ${MKFS_UBI_MAX_ERASE_BLK_COUNT} ${MKFS_EXTRA}"
UBINIZE_ARGS = "-m 2048 -p 128KiB"

IMAGEDIR ?= "vuplus/${UPDATE_DIR}"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash omb"

BOOTLOGO_FILENAME = "splash_cfe_auto.bin"

require conf/machine/include/tune-mips32.inc

