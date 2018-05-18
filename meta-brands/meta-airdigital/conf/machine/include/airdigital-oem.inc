# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "airdigital"

MACHINE_NAME = "\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash1', 'Star H1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash2', 'Star H2' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmas2s', 'Star 2S' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmass', 'Star S' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmahs', 'H.S' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2s', 'H.2S' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2h', 'H.2H' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmaslc', 'Star LC' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah4', 'H4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5', 'H5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah6', 'H6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52s', 'H5.2S' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52tc', 'H5.2TC' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmai55', 'I55' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novacombo', 'NOVA COMBO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novaip', 'NOVA IP' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novatwin', 'NOVA TWIN' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah3ac', 'H3 AC' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5ac', 'H5 AC' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah32tc', 'H3.2TC' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah7', 'H7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52splus', 'H5.2S PLUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2splus', 'H.2S PLUS' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9s', 'H9S' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9t', 'H9T' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9splus', 'H9S PLUS' , '', d)}\
"

MACHINE_BRAND = "\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash1', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash2', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmas2s', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmass', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmahs', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2s', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2h', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmaslc', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah4', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah6', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52s', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52tc', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmai55', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novacombo', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novaip', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novatwin', 'EVO' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah3ac', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5ac', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah32tc', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah7', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52splus', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2splus', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9s', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9t', 'Zgemma' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9splus', 'Zgemma' , '', d)}\
"

IMAGEDIR ?= "\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash1', 'zgemma/sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash2', 'zgemma/sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmas2s', 'zgemma/sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmass', 'zgemma/sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmahs', 'zgemma/h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2s', 'zgemma/h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2h', 'zgemma/h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmaslc', 'zgemma/lc' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah4', 'zgemma/h4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5', 'zgemma/h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah6', 'zgemma/h6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52s', 'zgemma/h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52tc', 'zgemma/h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmai55', 'zgemma/i55' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novacombo', 'novacombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novaip', 'novaip' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novatwin', 'novatwin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah3ac', 'zgemma/h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5ac', 'zgemma/h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah32tc', 'zgemma/h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah7', 'zgemma/h7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52splus', 'zgemma/h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2splus', 'zgemma/h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9s', 'h9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9t', 'h9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9splus', 'h9' , '', d)}\
"

MACHINE_DRIVER = "\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash1', 'sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmash2', 'sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmas2s', 'sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmass', 'sh1' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmahs', 'h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2s', 'h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2h', 'h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmaslc', 'lc' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah4', 'h4' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5', 'h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah6', 'h6' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52s', 'h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52tc', 'h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmai55', 'i55' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novacombo', 'novacombo' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novaip', 'novaip' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'novatwin', 'novatwin' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah3ac', 'h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah5ac', 'h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah32tc', 'h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah7', 'h7' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah52splus', 'h5' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah2splus', 'h3' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9s', 'h9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9t', 'h9' , '', d)}\
${@bb.utils.contains('MACHINEBUILD', 'zgemmah9splus', 'h9' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS = "\
    airdigital-dvb-modules-${MACHINE_DRIVER} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil', 'airdigital-libs-${MACHINE} airdigital-partitions-${MACHINE}' , '', d)} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qtegls', 'airdigital-qteglfs-platform', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'swap', 'airdigital-swapcreate', '',d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'airdigital-mali-${MACHINE} kernel-module-mali-${MACHINE} ffmpeg' , '', d)} \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    ${@bb.utils.contains('MACHINE', 'h9', '' , 'gstreamer1.0-libav ', d)} \
    "

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 \
    ${@bb.utils.contains('MACHINE', 'h5', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'h6', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'h7', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
    ${@bb.utils.contains('MACHINE', 'h9', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
    "

PREFERRED_PROVIDER_virtual/kernel = "linux-airdigital"
PREFERRED_PROVIDER_virtual/egl ?= "airdigital-v3ddriver-${MACHINE}" 
PREFERRED_PROVIDER_virtual/libgles2 ?= "airdigital-v3ddriver-${MACHINE}"
PREFERRED_PROVIDER_virtual/kodi = "stb-kodi-${MACHINE}"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff blindscan-tbs"

OEM_ARCH = "${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
