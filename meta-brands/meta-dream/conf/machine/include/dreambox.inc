# dreambox.inc
# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dreambox"
MACHINE_BRAND = "Dreambox"

EXTRA_IMAGEDEPENDS += "dreambox-secondstage-${MACHINE}"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

DVBMEDIASINK_CONFIG = "--with-dreambox --with-pcm --with-wma --with-wmv --with-dtsdownmix --with-eac3 --with-dts"
DVBMEDIASINK_CONFIG_dm7080 = "--with-dreambox --with-pcm --with-wma --with-wmv --with-dtsdownmix --with-eac3 --with-dts --with-vb6 --with-vb8 --with-spark"
DVBMEDIASINK_CONFIG_dm820 = "--with-dreambox --with-pcm --with-wma --with-wmv --with-dtsdownmix --with-eac3 --with-dts --with-vb6 --with-vb8 --with-spark"
DVBMEDIASINK_CONFIG_dm520 = "--with-dreambox --with-pcm --with-wma --with-wmv --with-dtsdownmix --with-eac3 --with-dts --with-vb6 --with-vb8 --with-spark --with-h265"

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    kernel-module-cifs \
    kernel-module-autofs4 \
    kernel-module-hmac \
    kernel-module-md4 \
    kernel-module-ecb \
    tpmd \
    kernel-module-fuse \
    kernel-module-nfsd \
    kernel-module-exportfs \
    kernel-module-snd-oss \
    kernel-module-snd-pcm-oss \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d', 'libvc5dream', '',d)} \
    "
    
MACHINE_FEATURES += "dreambox alsa usbhost 3dtv switchoff tpm blindscan-dvbs boxmodel feature1 feature2"
# Add "dreambox" override for recipes
MACHINEOVERRIDES .= ":dreambox"

PREFERRED_PROVIDER_virtual/bootloader = "dreambox-secondstage-${MACHINE}"
PREFERRED_PROVIDER_virtual/kernel = "linux-dreambox"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "dreambox-blindscan-utils"

PREFERRED_VERSION_linux-dreambox ?= "3.2"
PREFERRED_VERSION_linux-libc-headers = "3.2"
PREFERRED_VERSION_nfs-utils ?= "1.2.8"
PREFERRED_VERSION_rt8812au = "4.3.14"

DREAMBOX_BUILDIMAGE = "buildimage --arch ${MACHINE} ${EXTRA_BUILDCMD} \
    --erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZE} \
    --sector-size ${DREAMBOX_SECTOR_SIZE}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
