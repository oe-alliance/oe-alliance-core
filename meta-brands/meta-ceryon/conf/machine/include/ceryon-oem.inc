# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "ceryon"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcd', 'TWINBOX LCD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcdci5', 'TWINBOX LCD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicro', 'Premium Micro' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicrov2', 'Premium Micro 2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odin2hybrid', 'ODIN2 Hybrid' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odinplus', 'ODIN2 Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hd', 'E4HD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'E4HD HYBRID' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'E4HD COMBO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'singleboxlcd', 'SINGLEBOX LCD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf208', 'SF208 E2 HD LCD SINGLE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf228', 'SF228 E2 HD LCD TWIN' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf238', 'SF228 E2 HD LCD TWIN' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9910lx', '9910LX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9911lx', '9911LX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7252', '8100S' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcd', 'RED EAGLE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcdci5', 'RED EAGLE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicro', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicrov2', 'Miraclebox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odin2hybrid', 'Opticum' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odinplus', 'Opticum' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hd', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'AXAS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'singleboxlcd', 'RED EAGLE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf208', 'OCTAGON' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf228', 'OCTAGON' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf238', 'OCTAGON' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9910lx', 'Protek' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9911lx', 'Protek' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7252', 'Broadcom' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcd', 'redeagle/twinboxlcd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcdci5', 'redeagle/twinboxlcd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicro', 'miraclebox/micro' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicrov2', 'miraclebox/microv2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odin2hybrid', 'odin2/hybrid' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odinplus', 'odinplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hd', 'e4hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'e4hd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'e4hdcombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'singleboxlcd', 'redeagle/singleboxlcd' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf208', 'sf208' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf228', 'sf228' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf238', 'sf228' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9910lx', '9910lx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9911lx', '9911lx' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7252', '8100S' , '', d)}\
"

MTD_KERNEL ?= "\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcd', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcdci5', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicro', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicrov2', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odin2hybrid', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odinplus', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hd', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'singleboxlcd', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf208', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf228', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf238', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9910lx', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9911lx', 'mtd1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7252', 'mmcblk0p1' , '', d)}\
"

MTD_ROOTFS ?= "\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcdci5', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicro', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicrov2', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odin2hybrid', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odinplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'singleboxlcd', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf208', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf228', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf238', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9910lx', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9911lx', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7252', 'mmcblk0p7' , '', d)}\
"

KERNEL_FILE ?= "\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcd', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcdci5', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicro', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicrov2', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odin2hybrid', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odinplus', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hd', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'singleboxlcd', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf208', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf228', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf238', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9910lx', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9911lx', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7252', 'kernel.bin' , '', d)}\
"

ROOTFS_FILE ?= "\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcd', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'twinboxlcdci5', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicro', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'mbmicrov2', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odin2hybrid', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'odinplus', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hd', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'singleboxlcd', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf208', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf228', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'sf238', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9910lx', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', '9911lx', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'bcm7252', 'rootfs.tar.bz2' , '', d)}\
"

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
    ${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-plugin-dvbmediasink", "gst-plugin-dvbmediasink", d)} \
    "

include conf/machine/include/autoload-filesystems.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 \
    ${@bb.utils.contains('MACHINE', '7005s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', '7105s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', '7215s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', '7225s', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', '8100s', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "ceryon-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "ceryon-blindscan-utils"

PREFERRED_PROVIDER_virtual/kernel = "linux-ceryon"


MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash omb videoenhancement directfb skins1080 blindscan-tbs \
    ${@bb.utils.contains('MACHINEBUILD', 'e4hdhybrid', 'dvb-c dvbc-only' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'e4hdcombo', 'dvb-c' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', '9911lx', 'dvb-c' , '', d)} \
"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/soc-family.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
