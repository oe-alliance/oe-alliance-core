# dreambox-arm.inc
# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "dreambox"
MACHINE_BRAND = "Dreambox"

DVBSKYPROVIDER = "kernel"

# the audio decoder of the dreamaudiod service, the video through the Dream decoder interface;
# H.263 and Sorenson Spark reach the MPEG-4 decoder through the flv2mpeg4 element
DVBMEDIASINK_CONFIG = "--with-pcm --with-eac3 --with-dts --with-aac-loas --with-audio-device=/dev/dvb/adapter0/dreamaudio0 --with-h265 --with-wmv --with-vb9 --with-vb6=no --with-vb8=no --with-spark=no --with-h263=no"

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
    gstreamer1.0-plugin-dvbmediasink \
    gstreamer1.0-plugins-ugly-realmedia \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-ugly-dvdlpcmdec \
    gstreamer1.0-plugins-ugly-realmedia \
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
    dreammosaic \
    meson-mali-module-bifrost-r12p0 \
    flash-nrf52 \
    nrf52-firmware-${MACHINEBUILD} \
    nrf52-firmware-rcu \
    tpmd \
    tpm-ca \
    dreambox-bootlogo \
    fbset \
    fbset-modes \
    dp-device \
    dream-gpt-startup \
    bcm4356-bt-firmware \
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    u-boot-bin \
    dreamaudiod \
"

MACHINE_FEATURES += "dreambox alsa usbhost 3dtv switchoff blindscan-dvbs kodi22 inibt"
# Add "dreambox" override for recipes
MACHINEOVERRIDES .= ":dreambox"

PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${SOC_FAMILY}"

VIRTUAL-RUNTIME_blindscan_dvbs = "dreambox-blindscan-utils"
VIRTUAL-RUNTIME_blindscan_dvbc = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"
PREFERRED_PROVIDER_virtual/fftw = "fftw"

PREFERRED_PROVIDER_virtual/kernel = "linux-${SOC_FAMILY}"
PREFERRED_VERSION_linux-${SOC_FAMILY} = "4.9.%"

MACHINE_FEATURES_OPTED_OUT = "rtc gobject-introspection-data"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"

PACKAGECONFIG:ffmpeg:append = "libv4l2"
EXTRA_OECONF:append:pn-enigma2 = " --with-alsa  --with-alsacard=hw:0  --with-alsamixer=Master --with-fbdev=/dev/fb0 --with-lcddev=/dev/null "
EXTRA_OECONF:append:pn-tuxtxt-enigma2 = " --with-fbdev=/dev/fb0"
