# We know about rtc
include conf/machine/include/build-extras.inc

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'HD-x' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', '1000-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', '5000-HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'marvel', 'Marvel' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'LX-x' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'LX-3' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'Premium Mini' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'Premium Twin' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'UNiBOX' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', 'Sezam' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', 'Sezam' , '', d)}\
${@base_contains('MACHINEBUILD', 'marvel', 'Sezam' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'GI' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'GI' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'Miraclebox' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'Miraclebox' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'ventonhdx', 'venton-hdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam1000hd', 'hde' , '', d)}\
${@base_contains('MACHINEBUILD', 'sezam5000hd', 'hdx' , '', d)}\
${@base_contains('MACHINEBUILD', 'marvel', 'hdp' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx', 'xpeedlx' , '', d)}\
${@base_contains('MACHINEBUILD', 'xpeedlx3', 'xpeedlx3' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmini', 'miraclebox/mini' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbtwin', 'miraclebox/twin' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINE_OEM', 'inihde', 'mtd2' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdx', 'mtd2' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdp', 'mtd0' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINE_OEM', 'inihde', 'mtd2' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdx', 'mtd1' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdp', 'mtd2' , '', d)}\
"

MKUBIFS_ARGS = "\
${@base_contains('MACHINE_OEM', 'inihde', '-m 2048 -e 126976 -c 4096' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdx', '-m 2048 -e 126976 -c 4096' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdp', '-m 4096 -e 1040384 -c 1984' , '', d)}\

UBINIZE_ARGS = "\
${@base_contains('MACHINE_OEM', 'inihde', '-m 2048 -p 128KiB' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdx', '-m 2048 -p 128KiB' , '', d)}\
${@base_contains('MACHINE_OEM', 'inihdp', '-m 4096 -p 1024KiB' , '', d)}\

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
	ini-dvb-modules-${MACHINE_OEM} \
	"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gst-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

GLIBC_EXTRA_OECONF = "--with-tls"
DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts"

PREFERRED_PROVIDER_virtual/kernel = "linux-ini"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "ini-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "ini-blindscan-utils"

PREFERRED_PROVIDER_virtual/transtreamproxy = "ini-transtreamproxy"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash dvb-c blindscan-dvbc blindscan-dvbs ci"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"
