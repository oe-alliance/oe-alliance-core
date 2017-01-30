# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "gigablue"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'gb800se', '800 SE' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', '800 SE Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', '800 Solo' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', '800 UE' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', '800 UE Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'IPBox' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'Quad' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'Quad Plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'Ultra SE' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'Ultra UE' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'X1' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'X3' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'X2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'Ultra UEh' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'X3h' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'UHD QUAD 4k' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'gb800se', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'GigaBlue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'GigaBlue' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'gb800se', 'gigablue/se' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', 'gigablue/seplus' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', 'gigablue/solo' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', 'gigablue/ue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', 'gigablue/ueplus' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'gigablue/ipbox' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'gigablue/quad' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'gigablue/quadplus' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'gigablue/ultrase' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'gigablue/ultraue' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'gigablue/x1' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'gigablue/x3' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'gigablue/x2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'gigablue/ultraueh' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'gigablue/x3h' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'gigablue/uhdquad' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINEBUILD', 'gb800se', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'mtd2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'mmcblk0p1' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINEBUILD', 'gb800se', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'mtd0' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'mmcblk0p4' , '', d)}\
"

MACHINE_DRIVER = "\
${@base_contains('MACHINEBUILD', 'gb800se', 'gb7325' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', 'gb7358' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', 'gb7325' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', 'gb7325' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', 'gb7358' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'gb7358' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'gb7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'gb7356' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'gb7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'gb7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'gb7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'gb7362' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'gb73625' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'gb73625' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'gb73625' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'gb7252' , '', d)}\
"

KERNEL_FILE = "\
${@base_contains('MACHINEBUILD', 'gb800se', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'kernel.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'kernel.bin' , '', d)}\
"

ROOTFS_FILE = "\
${@base_contains('MACHINEBUILD', 'gb800se', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800seplus', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800solo', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ue', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gb800ueplus', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbipbox', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquad', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbquadplus', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultrase', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraue', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx1', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx2', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbultraueh', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbx3h', 'rootfs.bin' , '', d)}\
${@base_contains('MACHINEBUILD', 'gbuhdquad', 'rootfs.tar.bz2' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
    gigablue-dvb-modules-${MACHINE_DRIVER} \
    gigablue-ddbootup \
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
    ${@base_contains('MACHINEBUILD', 'gbquad', 'kernel-module-smsc75xx' , '', d)} \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    gigablue-cfe \
    ${@base_contains('DISTRO_FEATURES', 'audioeffect', 'enigma2-plugin-systemplugins-audioeffect' , '', d)} \
    "

EXTRA_IMAGEDEPENDS += "\
    enigma2-plugin-systemplugins-vfdcontrol \
    "

MACHINE_EXTRA_RDEPENDS = "\
    enigma2-plugin-systemplugins-vfdcontrol \
    ${@base_contains('MACHINEBUILD', 'gb800solo', 'mtd-utils-jffs2' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'gbipbox', 'enigma2-plugin-extensions-gbipboxclient' , '', d)} \
"

include conf/machine/include/autoload-filesystems.inc

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "gigablue-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "gigablue-blindscan-utils"
PREFERRED_PROVIDER_virtual/transtreamproxy = "gb-transtreamproxy"

PREFERRED_PROVIDER_virtual/kernel = "linux-gigablue"

KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash boxmodel skins1080 blindscan-tbs"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/soc-family.inc

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"