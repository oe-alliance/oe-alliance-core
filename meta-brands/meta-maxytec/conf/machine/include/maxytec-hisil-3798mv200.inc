MACHINE_FEATURES += "qthbbtv qtstalker linuxwifi"

PREFERRED_PROVIDER_virtual/egl ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libopencl ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgbm ?= "maxytec-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"

PREFERRED_PROVIDER_virtual/blindscan-dvbc = "maxytec-dvb-modules-${MACHINE}"
PREFERRED_PROVIDER_virtual/blindscan-dvbs = "maxytec-dvb-modules-${MACHINE}"

# Qt
PACKAGECONFIG_GL_pn-qtbase = "no-opengl linuxfb"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-${HICHIPSET}', 'maxytec-bootlogo-${MACHINE} maxytec-libs-${HICHIPSET} maxytec-param-${MACHINE} maxytec-bootargs-${MACHINE} maxytec-fastboot-${MACHINE}' , '', d)} \
    "

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'maxytec-mali-${HICHIPSET} kernel-module-mali-${HICHIPSET}' , '', d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-3798mv200', 'ffmpeg gstreamer1.0-libav' , 'gstreamer1.0-libav ', d)} \
    "

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-maxytec = "4.4.35"

require conf/machine/include/tune-cortexa15.inc
require conf/machine/include/maxytec-oem.inc