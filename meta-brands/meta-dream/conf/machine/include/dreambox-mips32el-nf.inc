# dreambox-mips32el.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el-nf"

MTD_KERNEL = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

EXTRA_IMAGEDEPENDS += "dreambox-secondstage-${MACHINE}"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

DVBMEDIASINK_CONFIG = "--with-dreambox --with-dts=no"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    dreambox-dvb-modules-${MACHINE} \
    kernel-module-dvb-core \
    kernel-module-stv0299 \
"

include conf/machine/include/autoload-usbserial.inc

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    ntfs-3g \
    kernel-module-hmac \
    kernel-module-md4 \
    tpmd \
"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cifs \
    kernel-module-ntfs \
    kernel-module-iso9660 \
    kernel-module-udf \
    kernel-module-nls-cp850 \
    kernel-module-fuse \
    kernel-module-nfsd \
    kernel-module-exportfs \
    kernel-module-snd-oss \
    kernel-module-snd-pcm-oss \
    "

KERNEL_MODULE_AUTOLOAD += "cifs"
KERNEL_MODULE_AUTOLOAD += "iso9660"
KERNEL_MODULE_AUTOLOAD += "udf"
KERNEL_MODULE_AUTOLOAD += "ntfs"

MACHINE_FEATURES += "dreambox alsa usbhost tpm kernel26-legacy"

PREFERRED_PROVIDER_virtual/bootloader = "dreambox-secondstage-${MACHINE}"
PREFERRED_PROVIDER_virtual/kernel = "linux-dreambox"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "dreambox-blindscan-utils"

PREFERRED_VERSION_linux-dreambox = "2.6.18"
PREFERRED_VERSION_linux-libc-headers = "2.6.18"
OLDEST_KERNEL = "2.6.18"

PREFERRED_VERSION_wpa-supplicant = "0.5.8"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "${@bb.utils.contains('MACHINE_FEATURES', 'usbconsole', 'ttyS0,115200', 'null', d)}"

MKUBIFS_ARGS = "-m 2048 -e 129024 -c 1996 -x favor_lzo -X 1"
UBINIZE_ARGS = "-m 2048 -p 128KiB -s 512"

DREAMBOX_BUILDIMAGE = "buildimage --arch ${MACHINE} ${EXTRA_BUILDCMD} \
    --erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZE} \
    --flash-size ${DREAMBOX_FLASH_SIZE} \
    --sector-size ${DREAMBOX_SECTOR_SIZE}"

IMAGE_FSTYPES ?= "jffs2nfi"

require conf/machine/include/tune-mips32.inc
