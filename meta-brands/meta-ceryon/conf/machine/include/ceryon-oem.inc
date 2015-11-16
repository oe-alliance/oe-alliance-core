# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "ceryon"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'twinboxlcd', 'TWINBOX LCD' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicro', 'Premium Micro' , '', d)}\
${@base_contains('MACHINEBUILD', 'odin2hybrid', 'ODIN2' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hd', 'E4HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdc', 'E4HD Hybrit' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'twinboxlcd', 'RED EAGLE' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicro', 'Miraclebox' , '', d)}\
${@base_contains('MACHINEBUILD', 'odin2hybrid', 'Opticum' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hd', 'AXAS' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdc', 'AXAS' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'twinboxlcd', 'redeagle/twinboxlcd' , '', d)}\
${@base_contains('MACHINEBUILD', 'mbmicro', 'miraclebox/micro' , '', d)}\
${@base_contains('MACHINEBUILD', 'odin2hybrid', 'odin2/hybrid' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hd', 'e4hd' , '', d)}\
${@base_contains('MACHINEBUILD', 'e4hdc', 'e4hd' , '', d)}\
"

MTD_KERNEL = "mtd1"
MTD_ROOTFS = "mtd2"
KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    ceryon-dvb-modules-${MACHINE} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    ceryon-shutdown \
    gst-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

PREFERRED_PROVIDER_virtual/kernel = "linux-ceryon"

PREFERRED_VERSION_linux-ceryon = "4.0.1"
PREFERRED_VERSION_linux-libc-headers = "4.0"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

IMAGE_FSTYPES ?= "ubi"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CMD_ubi_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/boot/*; \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff wifi bootsplash omb directfb sdl"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/tune-mips32.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"
