# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "xcore"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'SpyCat' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'SpyCat mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'SpyCat mini +' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'SpyCat 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'SpyCat 4K Combo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'SpyCat 4K mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', '7358ci' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', '7358' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'OS mini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'OS mini +' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'OS mega' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'SPYCAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'SPYCAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'SPYCAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'SPYCAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'SPYCAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'SPYCAT' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', 'Sample2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', 'Sample1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'Edision' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'Edision' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'spycat' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'spycatmini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'spycatminiplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'spycat4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'spycat4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'spycat4kmini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', 'vp7358ci' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', 'bcm7358' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'osmini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'osminiplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'osmega' , '', d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'mmcblk1p2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'mmcblk1p2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'mtd0' , '', d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'mmcblk1p3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'mmcblk1p3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', 'mtd3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'mtd2' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'spycat' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'spycatmini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'spycatminiplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'spycat4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'spycat4kcombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'spycat4kmini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', 'vp7358ci' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', 'bcm7358' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'osmini' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'osminiplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'osmega' , '', d)}\
"

KERNEL_FILE = "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'kernel.bin' , '', d)}\
"

ROOTFS_FILE  = "\
${@bb.utils.contains('MACHINEBUILD', 'spycat', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatmini', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4k', 'rootfs.tar.bz2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kcombo', 'rootfs.tar.bz2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'spycat4kmini', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'vp7358ci', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7358', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmini', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osminiplus', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'osmega', 'rootfs.bin' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    xcore-dvb-modules-${MACHINE_DRIVER} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    kernel-module-cdfs \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d', 'xcore-v3ddriver-${MACHINE}', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'swap', 'xcore-swapcreate', '',d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3  \
    ${@bb.utils.contains('MACHINEBUILD', 'osminiplus', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'spycatminiplus', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'osmega', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'xc7439', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
"

PREFERRED_PROVIDER_virtual+blindscan-dvbs = "xcore-blindscan-utils"
PREFERRED_PROVIDER_virtual+blindscan-dvbc = "xcore-blindscan-utils"

PREFERRED_PROVIDER_virtual/kernel = "linux-xcore"
PREFERRED_PROVIDER_virtual/egl = "xcore-v3ddriver-${MACHINE}" 
PREFERRED_PROVIDER_virtual/libgles2 = "xcore-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

MACHINE_FEATURES += "alsa 3dtv switchoff hdmicec bootsplash omb boxmodel"

BOOTLOGO_FILENAME = "splash.bin"

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"