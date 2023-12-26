# dreambox-arm.inc
# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dreambox"
MACHINE_BRAND = "Dreambox"

DVBSKYPROVIDER = "kernel"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"
TARGET_ARCH = "aarch64"
DEFAULTTUNE = "aarch64"

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc
require conf/machine/include/arm/arch-armv8a.inc

# temp off gcc 10 build issue
#INHERIT += "opendreambox-multilib"
#require conf/machine/multilib.inc

MACHINE_EXTRA_RRECOMMENDS += "\
    ffmpeg \
    enigma2-plugin-systemplugins-serviceapp \
    gstreamer1.0-dreamsinks \
    gstreamer1.0-libav \
    kernel-module-cifs \
    kernel-module-autofs4 \
    kernel-module-hmac \
    kernel-module-md4 \
    kernel-module-ecb \
    kernel-module-fuse \
    kernel-module-nfsd \
    kernel-module-exportfs \
    kernel-module-snd-oss \
    kernel-module-snd-pcm-oss \
    kernel-modules \
"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    bcmdhd \
    dreambox-dvb-modules-${SOC_FAMILY} \
    meson-mali-module-bifrost-r12p0 \
    flash-nrf52 \
    nrf52-firmware-${MACHINE} \
    nrf52-firmware-rcu \
    tpmd \
    tpm-ca \
    dreambox-bootlogo \
    fbset \
    fbset-modes \
    dp-device \
    dream-gpt-startup \
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    u-boot-bin \
"

MACHINE_FEATURES += "dreambox alsa usbhost 3dtv switchoff blindscan-dvbs"
# Add "dreambox" override for recipes
MACHINEOVERRIDES .= ":dreambox"

PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${SOC_FAMILY}"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"
PREFERRED_PROVIDER_virtual/fftw = "fftw"

PREFERRED_PROVIDER_virtual/kernel = "linux-${SOC_FAMILY}"
PREFERRED_VERSION_linux-${SOC_FAMILY} = "4.9.%"

MACHINE_FEATURES_BACKFILL_CONSIDERED = "rtc gobject-introspection-data"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

PACKAGECONFIG:ffmpeg:append = "libv4l2"
EXTRA_OECONF:append:pn-enigma2 = " --with-alsa  --with-alsacard=hw:0:0  --with-alsamixer=Master --with-fbdev=/dev/fb0 --with-lcddev=/dev/null "
EXTRA_OECONF:append:pn-tuxtxt-enigma2 = " --with-fbdev=/dev/fb0"
