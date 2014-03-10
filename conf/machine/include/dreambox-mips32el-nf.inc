# dreambox-mips32el.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el-nf"

EXTRA_IMAGEDEPENDS += "dreambox-secondstage-${MACHINE}"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    dreambox-dvb-modules-${MACHINE} \
    ${@base_conditional('MACHINE', 'dm800', 'kernel-module-dvb-core', 'v4l-dvb-module-dvb-core', d)} \
    ${@base_conditional('MACHINE', 'dm800', 'kernel-module-stv0299', 'v4l-dvb-module-stv0299', d)} \
"

include conf/machine/include/autoload-usbserial.inc

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dreambox-dvbmediasink \
    ntfs-3g \
    kernel-module-hmac \
    kernel-module-md4 \
    tpmd \
"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cifs \
    kernel-module-ntfs \
    kernel-module-iso9660 \
    kernel-module-udf \
    kernel-module-nls-cp850 \
    "
module_autoload_cifs = "cifs"
module_autoload_iso9660 = "iso9660"
module_autoload_udf = "udf"
module_autoload_ntfs = "ntfs"

MACHINE_FEATURES += "dreambox alsa usbhost tpm kernel26-legacy"

PREFERRED_PROVIDER_virtual/bootloader = "dreambox-secondstage-${MACHINE}"
PREFERRED_PROVIDER_virtual/kernel = "linux-dreambox"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "dreambox-blindscan-utils"

PREFERRED_VERSION_linux-dreambox = "2.6.18"
PREFERRED_VERSION_linux-libc-headers = "2.6.18"

PREFERRED_VERSION_wpa-supplicant = "0.5.8"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "${@base_contains('MACHINE_FEATURES', 'usbconsole', 'ttyS0,115200', 'null', d)}"

MKUBIFS_ARGS = "-m 2048 -e 129024 -c 1996 -x favor_lzo -X 1"
UBINIZE_ARGS = "-m 2048 -p 128KiB -s 512"

DREAMBOX_BUILDIMAGE = "buildimage --arch ${MACHINE} ${EXTRA_BUILDCMD} \
    --erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZE} \
    --flash-size ${DREAMBOX_FLASH_SIZE} \
    --sector-size ${DREAMBOX_SECTOR_SIZE}"

IMAGE_FSTYPES ?= "jffs2.nfi"

require conf/machine/include/tune-mips32.inc

