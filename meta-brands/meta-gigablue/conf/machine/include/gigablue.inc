# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "gigablue"
MACHINE_BRAND = "GigaBlue"

MTD_ROOTFS = "mtd0"
MTD_KERNEL = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
    gigablue-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = " \
    kernel-module-cdfs \
    kernel-module-zram \
    kernel-module-ext2 \
    kernel-module-ext3 \
    kernel-module-ext4 \
    kernel-module-cifs \
    kernel-module-autofs4 \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS_append_gbquad = "kernel-module-smsc75xx"
# MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS_append_gbquadplus = "kernel-module-smsc75xx"

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    gigablue-cfe \
    "

include conf/machine/include/autoload-filesystems.inc
    
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "gigablue-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "gigablue-blindscan-utils-${MACHINE}"
PREFERRED_PROVIDER_virtual/transtreamproxy = "gb-transtreamproxy"
    
DVBMEDIASINK_CONFIG = "--with-wmv"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"

PREFERRED_PROVIDER_virtual/kernel = "linux-gigablue"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash boxmodel audioeffect"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

