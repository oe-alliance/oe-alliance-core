# We know about rtc
include conf/machine/include/build-extras.inc

BRAND_OEM = "gfutures"

MACHINE_NAME = "\
${@base_contains('MACHINEBUILD', 'mutant1100', 'HD1100' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1265', 'HD1265' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1500', 'HD1500' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1100', 'HD1100' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant500c', 'HD500C' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant530c', 'HD530C' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1200', 'HD1200' , '', d)}\
${@base_contains('MACHINEBUILD', 'vizyonvita', 'Vita' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant11', 'HD11' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant51', 'HD51' , '', d)}\
${@base_contains('MACHINEBUILD', 'ax51', 'HD51' , '', d)}\
${@base_contains('MACHINEBUILD', 'bre2ze4k', 'BRE2ZE 4K' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1000', 'VS1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1500', 'VS1500' , '', d)}\
"

MACHINE_BRAND = "\
${@base_contains('MACHINEBUILD', 'mutant1100', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1265', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1500', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant500c', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant530c', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1200', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'vizyonvita', 'Vizyon' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant11', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant51', 'Mut@nt' , '', d)}\
${@base_contains('MACHINEBUILD', 'ax51', 'AX 4K' , '', d)}\
${@base_contains('MACHINEBUILD', 'bre2ze4k', 'WWIO' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1000', 'Vimastec' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1500', 'Vimastec' , '', d)}\
"

IMAGEDIR ?= "\
${@base_contains('MACHINEBUILD', 'mutant1100', 'hd1100' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1265', 'hd1265' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1500', 'hd1500' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant500c', 'hd500c' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant530c', 'hd530c' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1200', 'hd1200' , '', d)}\
${@base_contains('MACHINEBUILD', 'vizyonvita', 'hd1100' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant11', 'hd11' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant51', 'hd51' , '', d)}\
${@base_contains('MACHINEBUILD', 'ax51', 'hd51' , '', d)}\
${@base_contains('MACHINEBUILD', 'bre2ze4k', 'bre2ze4k' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1000', 'vs1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1500', 'vs1500' , '', d)}\
"

MACHINE_DRIVER ?= "\
${@base_contains('MACHINEBUILD', 'mutant1100', 'hd1100' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1265', 'hd1265' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1500', 'hd1500' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant500c', 'hd500c' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant530c', 'hd530c' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant1200', 'hd1200' , '', d)}\
${@base_contains('MACHINEBUILD', 'vizyonvita', 'hd1100' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant11', 'hd11' , '', d)}\
${@base_contains('MACHINEBUILD', 'mutant51', 'hd51' , '', d)}\
${@base_contains('MACHINEBUILD', 'ax51', 'hd51' , '', d)}\
${@base_contains('MACHINEBUILD', 'bre2ze4k', 'bre2ze4k' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1000', 'vs1000' , '', d)}\
${@base_contains('MACHINEBUILD', 'vimastec1500', 'vs1500' , '', d)}\
"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    gfutures-dvb-modules-${MACHINE_DRIVER} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS = "\
    kernel-module-cdfs \
    "

MACHINE_EXTRA_RRECOMMENDS = " \
    gstreamer1.0-plugin-dvbmediasink \
    "

include conf/machine/include/autoload-filesystems.inc
require conf/machine/include/soc-family.inc

DVBMEDIASINK_CONFIG = "--with-wma --with-wmv --with-pcm --with-dts --with-eac3 \
    ${@base_contains('MACHINEBUILD', 'mutant1265', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'mutant1500', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINEBUILD', 'mutant11', '--with-h265 --with-vb6 --with-vb8 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', 'hd51', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', 'vs1500', '--with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark' , '', d)} \
    ${@base_contains('MACHINE', 'vs1000', '--with-h265 --with-vb6 --with-vb8  --with-spark' , '', d)} \
"

PREFERRED_PROVIDER_virtual/kernel = "linux-gfutures"

MACHINE_FEATURES += "3dtv alsa hdmicec switchoff blindscan-tbs"

EXTRA_IMAGEDEPENDS += " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "

MACHINE_EXTRA_RDEPENDS_${PN} = " \
    enigma2-plugin-systemplugins-remotecontrolcode \
    "

# add support for extra feeds
PACKAGE_ARCHS += " ${MACHINEBUILD}"

# Add "MACHINEBUILD" override for recipes
MACHINEOVERRIDES =. "${@['', '${MACHINEBUILD}:']['${MACHINEBUILD}' != '']}"
