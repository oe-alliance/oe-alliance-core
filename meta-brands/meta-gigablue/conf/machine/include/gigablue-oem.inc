PACKAGECONFIG_GL:pn-qtbase ?= "gles2 eglfs linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "gigablue"

RCTYPE = "502"
RCNAME = "gb1"
RCHARDWARE = "GB"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', '800 SE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', '800 SE Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', '800 Solo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', '800 UE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', '800 UE Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'IPBox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'Quad' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'Quad Plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'Ultra SE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'Ultra UE' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'X1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'X3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'X2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'Ultra UE H' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'X3 H' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'UHD QUAD 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'UHD UE 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'UHD TRIO 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'UHD TRIO 4K PRO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'UHD IP 4K' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'UHD X3 4K' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'GigaBlue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'GigaBlue' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'gigablue/se' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'gigablue/seplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'gigablue/solo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'gigablue/ue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'gigablue/ueplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'gigablue/ipbox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'gigablue/quad' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'gigablue/quadplus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'gigablue/ultrase' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'gigablue/ultraue' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'gigablue/x1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'gigablue/x3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'gigablue/x2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'gigablue/ultraueh' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'gigablue/x3h' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'gigablue/quad4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'gigablue/ue4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'gigablue/trio4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'gigablue/trio4kpro' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'gigablue/ip4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'gigablue/x34k' , '', d)}\
"

MTD_KERNEL = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'mtd2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'mmcblk0p4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'mmcblk0p12' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'mmcblk0p12' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'mmcblk0p12' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'mmcblk0p4' , '', d)}\
"

MTD_ROOTFS = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'mtd0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'mmcblk0p5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'mmcblk0p5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'mmcblk0p16' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'mmcblk0p16' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'mmcblk0p16' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'mmcblk0p5' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'gb7325' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'gb7358' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'gb7325' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'gb7325' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'gb7358' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'gb7358' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'gb7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'gb7356' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'gb7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'gb7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'gb7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'gb7362' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'gb73625' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'gb73625' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'gb73625' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'gb7252' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'gb7252' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'gbtrio4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'gbtrio4kpro' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'gbip4k' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'gb72604' , '', d)}\
"

KERNEL_FILE = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'kernel.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'kernel.bin' , '', d)}\
"

ROOTFS_FILE = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'rootfs.bin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'rootfs.tar.bz2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'rootfs.tar.bz2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'rootfs.tar.bz2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'rootfs.tar.bz2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'rootfs.tar.bz2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'rootfs.tar.bz2' , '', d)}\
"

RCNAME = "\
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'gb1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx1', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3', 'gb0' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx2', 'gb2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'gb2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx3h', 'gb2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'gb3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbue4k', 'gb4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'gb4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'gb5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'gb4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbx34k', 'gb3' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " \
    ${@bb.utils.contains("MACHINE_FEATURES", "nexusplatform", "gigablue-platform-util-${MACHINE_DRIVER}", "gigablue-dvb-modules-${MACHINE_DRIVER}", d)} \
    gigablue-ddbootup \
    "

DVBPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    kernel-module-cdfs \
    kernel-module-zram \
    kernel-module-ext2 \
    kernel-module-ext3 \
    kernel-module-ext4 \
    kernel-module-cifs \
    kernel-module-autofs4 \
    ${@bb.utils.contains('MACHINEBUILD', 'gbtrio4kpro', 'firmware-uwe5621' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'kernel-module-smsc75xx' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'swap', 'gigablue-swapcreate', '',d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-libav \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisi', '' , 'gigablue-cfe', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'audioeffect', 'enigma2-plugin-systemplugins-audioeffect' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb7252', 'gigablue-initrd-cfe-${MACHINEBUILD}' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb72604', 'gigablue-initrd-cfe-${MACHINEBUILD}' , '', d)} \
    "

EXTRA_IMAGEDEPENDS += "\
    enigma2-plugin-systemplugins-vfdcontrol \
    "

MACHINE_EXTRA_RDEPENDS += "\
    enigma2-plugin-systemplugins-vfdcontrol \
    ${@bb.utils.contains('MACHINE', 'gb7252', 'enigma2-plugin-systemplugins-remotecontrolcode' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gb72604', 'enigma2-plugin-systemplugins-remotecontrolcode' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'gbmv200', 'enigma2-plugin-systemplugins-remotecontrolcode' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'mtd-utils-jffs2' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'gbipbox', 'enigma2-plugin-extensions-gbipboxclient' , '', d)} \
    ${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'enigma2-plugin-extensions-gbipboxclient' , '', d)} \
"

include conf/machine/include/autoload-filesystems.inc

PREFERRED_PROVIDER_virtual/transtreamproxy = "gb-transtreamproxy"

PREFERRED_PROVIDER_virtual/kernel = "linux-gigablue"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_CONSOLE = "ttyS0,115200n8"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff bootsplash skins1080 blindscan-tbs HDMI \
${@bb.utils.contains('MACHINEBUILD', 'gb800se', 'SCART YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gb800seplus', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gb800solo', 'SCART YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gb800ue', 'SCART YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gb800ueplus', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gbquad', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gbquadplus', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gbultrase', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gbultraue', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gbultraueh', 'YUV RCA' , '', d)} \
${@bb.utils.contains('MACHINEBUILD', 'gbquad4k', 'HDMI-IN-FHD' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbtrio4k', 'AV_JACK' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'gbip4k', 'AV_JACK' , '', d)}\
"

BOOTLOGO_FILENAME = "splash.bin"

require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
