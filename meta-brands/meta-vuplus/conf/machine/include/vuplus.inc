# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "vuplus"
MACHINE_BRAND = "Vu+"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "dvbproxy", "vuplus-dvb-proxy-${MACHINE} vuplus-libgles-${MACHINE} vuplus-platform-util-${MACHINE}", "vuplus-dvb-modules-${MACHINE}", d)} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    vuplus-shutdown \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

include conf/machine/include/autoload-filesystems.inc

PREFERRED_VERSION_nfs-utils ?= "1.2.8"

PREFERRED_PROVIDER_virtual/kernel = "linux-vuplus"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "vuplus-blindscan-utils"
PREFERRED_PROVIDER_virtual/transtreamproxy = "vuplus-transtreamproxy"
PREFERRED_PROVIDER_virtual/xmbc = "vuplus-xbmc-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "kodi-${MACHINE}"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

UBI_VOLNAME = "rootfs"
MKUBIFS_ARGS = "-m 2048 -e 126976 -c ${MKFS_UBI_MAX_ERASE_BLK_COUNT} ${MKFS_EXTRA}"
UBINIZE_ARGS = "-m 2048 -p 128KiB"
EXTRA_IMAGECMD_COMPAT = "--eraseblock=0x20000 -n -l"


IMAGEDIR ?= "vuplus/${UPDATE_DIR}"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash"

BOOTLOGO_FILENAME = "splash_cfe_auto.bin"
