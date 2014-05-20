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

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
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


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi ci bootsplash boxmodel audioeffect"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

