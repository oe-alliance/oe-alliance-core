# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "fulan"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'SHD-8900 Alien' , '', d)}\
${@base_contains('MACHINEBUILD', 'amikomini', 'Alien-Mini' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'Spark Revolution' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'argus pingulux' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxmini', 'argus pingulux mini' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxplus', 'argus pingulux plus' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'SPARK TRIPLEX'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'SPARK RELOADED'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparklx', 'SPARK 990 LX'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkone', 'SPARK ONE'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'Alien 2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'Spark Triple' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabtriple', 'Unix Triple HD' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabsolo', 'Unix F+ Solo' , '', d)}\
${@base_contains('MACHINEBUILD', 'fulanspark1', 'Spark 1+' , '', d)}\
${@base_contains('MACHINEBUILD', 'giavatar', 'Avatar' , '', d)}\
${@base_contains('MACHINEBUILD', 'gis8120', 'S 8120' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark', 'SPARK' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynasparkplus', 'SPARK Plus HDPVR' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark7162', 'SPARK 7162' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'Amiko' , '', d)}\
${@base_contains('MACHINEBUILD', 'amikomini', 'Amiko' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'SOGNO' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxmini', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxplus', 'Edision' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'GOLDEN MEDIA'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'GOLDEN MEDIA'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparklx', 'GOLDEN MEDIA'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkone', 'GOLDEN MEDIA'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'Amiko' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'SOGNO' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabtriple', 'SAB' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabsolo', 'SAB' , '', d)}\
${@base_contains('MACHINEBUILD', 'fulanspark1', 'Fulan' , '', d)}\
${@base_contains('MACHINEBUILD', 'giavatar', 'Galaxy Innovations' , '', d)}\
${@base_contains('MACHINEBUILD', 'gis8120', 'Galaxy Innovations' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark', 'Dynavision' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynasparkplus', 'Dynavision' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark7162', 'Dynavision' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'amikomini', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxmini', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxplus', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'enigma2'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'enigma2'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparklx', 'enigma2'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkone', 'enigma2'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabtriple', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabsolo', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'fulanspark1', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'giavatar', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'gis8120', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynasparkplus', 'enigma2' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark7162', 'enigma2' , '', d)}\
"

MTD_KERNEL = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'amikomini', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxmini', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxplus', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'mtd5'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'mtd5'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparklx', 'mtd5'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkone', 'mtd5'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabtriple', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabsolo', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'fulanspark1', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'giavatar', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'gis8120', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynasparkplus', 'mtd5' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark7162', 'mtd5' , '', d)}\
"

MTD_ROOTFS = "\
${@base_contains('MACHINEBUILD', 'amiko8900', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'amikomini', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognorevolution', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspingulux', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxmini', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'arguspinguluxplus', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sparktriplex', 'mtd6'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkreloaded', 'mtd6'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparklx', 'mtd6'  , '', d)}\
${@base_contains('MACHINEBUILD', 'sparkone', 'mtd6'  , '', d)}\
${@base_contains('MACHINEBUILD', 'amikoalien', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sognotriple', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabtriple', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'sabsolo', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'fulanspark1', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'giavatar', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'gis8120', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynasparkplus', 'mtd6' , '', d)}\
${@base_contains('MACHINEBUILD', 'dynaspark7162', 'mtd6' , '', d)}\
"

KERNEL_FILE = "uImage"
ROOTFS_FILE = "e2jffs2.img"

MACHINE_KERNEL_PR = "r14"

TARGET_ARCH = "sh4"

GCCVERSION = "4.8%"

PREFERRED_PROVIDER_virtual/kernel = "linux-fulan"
PREFERRED_VERSION_linux-libc-headers = "2.6.32"
PREFERRED_VERSION_directfb = "1.4.12"
SERIAL_CONSOLE = "115200 ttyAS0"

DVBPROVIDER = "kernel"

MACHINE_FEATURES = "3dtv alsa hdmicec switchoff kernel26 ext2 usbhost serial dvb-c singlecore textlcd sh4booster no-nmap omb skins1080 directfb sdl"

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dtsdownmix --with-eac3"

KERNEL_IMAGETYPE = "uImage"
#KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"


JFFS2_ERASEBLOCK = "0x20000"
JFFS2_ENDIANNESS = "${@base_conditional('SITEINFO_ENDIANNESS', 'le', '--little-endian', '--big-endian', d)}"
EXTRA_IMAGECMD_jffs2 = "--pad ${JFFS2_ENDIANNESS} --eraseblock=${JFFS2_ERASEBLOCK} --no-cleanmarkers"
MKUBIFS_ARGS = "--pad ${JFFS2_ENDIANNESS} --eraseblock=${JFFS2_ERASEBLOCK} --no-cleanmarkers"

IMAGE_FSTYPES ?= "jffs2"

# enable this for debug
#IMAGE_FSTYPES += "tar.gz "
#IMAGE_FSTYPES += "spark71xx-usbimg "
#INHERIT += "usb_image-spark71xx"

IMAGE_CLASSES += "usb_image-spark71xx"
INITRAMFS_FSTYPES = "ext2.gz"

IMAGE_CMD_jffs2_prepend = " \
    rm -rf ${IMAGE_ROOTFS}/tmp/*; \
    "

IMAGE_CMD_jffs2_append = "; \
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.jffs2 ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${ROOTFS_FILE}; \
    cp ${DEPLOY_DIR_IMAGE}/uImage ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${KERNEL_FILE}; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_usb.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.jffs2; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/.jffs2; \
    rm -f ${DEPLOY_DIR_IMAGE}/.manifest; \
    rm -rf ${IMAGEDIR}; \
    "
	
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += " \
    stlinux24-sh4-stx7111-fdma-firmware \
    stlinux24-sh4-stmfb-firmware-stx7111 \
    stslave-fw \
    ustslave \
    stfbcontrol \
    fp-control \
    stlirc \
    stinit \
    fulan-dvb-modules-${MACHINE} \
    mtd-utils-jffs2 \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    enigma2-plugin-systemplugins-sh4boostercontrol \
    enigma2-plugin-systemplugins-sh4osdadjustment \
    "

EXTRA_IMAGEDEPENDS += "\
    enigma2-plugin-systemplugins-vfdcontrol \
    "

MACHINE_EXTRA_RDEPENDS = "\
    enigma2-plugin-systemplugins-vfdcontrol \
    "

include conf/machine/include/autoload-filesystems.inc

require conf/machine/include/tune-sh4.inc

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

