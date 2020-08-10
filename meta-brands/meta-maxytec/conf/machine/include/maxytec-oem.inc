# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "maxytec"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'ultrav8plus', 'V8PLUS Combo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolmulti', 'Multibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmulti', 'Multibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmultiplus', 'Multibox Plus' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'ultrav8plus', 'ULTRA BOX' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolmulti', 'ANADOL' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmulti', 'Maxytec' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmultiplus', 'Maxytec' , '', d)}\
"

IMAGEDIR = "\
${@bb.utils.contains('MACHINEBUILD', 'ultrav8plus', 'v8plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolmulti', 'multibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmulti', 'multibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmultiplus', 'plus' , '', d)}\
"

MACHINE_DRIVER ?= "\
${@bb.utils.contains('MACHINEBUILD', 'ultrav8plus', 'v8plus' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'anadolmulti', 'multibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmulti', 'multibox' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'maxytecmultiplus', 'plus' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    maxytec-dvb-modules-${MACHINE_DRIVER} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    kernel-module-cdfs \
    ${@bb.utils.contains('MACHINE_FEATURES', 'emmc', 'resizerootfs' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'adwol', 'maxytec-ethwol' , '', d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc
require conf/machine/box-hardware.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 --with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark"

PREFERRED_PROVIDER_virtual/kernel = "linux-maxytec"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff HDMI"

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
