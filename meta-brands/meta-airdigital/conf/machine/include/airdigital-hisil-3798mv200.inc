PACKAGECONFIG_GL:pn-qtbase = " "
PACKAGECONFIG:append:pn-qtbase = " gles2 linuxfb"

MACHINE_FEATURES += "qthbbtv qtstalker openhbbtv qtevent1 hisichromium"

PREFERRED_PROVIDER_virtual/egl ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles1 ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgles2 ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libopencl ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgbm ?= "airdigital-mali-${HICHIPSET}"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-${HICHIPSET}', 'airdigital-bootlogo-${HICHIPSET} airdigital-libs-${HICHIPSET} airdigital-param-${HICHIPSET} airdigital-bootargs-${HICHIPSET} airdigital-fastboot-${HICHIPSET} airdigital-tntfs-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisi-recovery', 'airdigital-recovery-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'SDcard', 'airdigital-bootargs-extra-${MACHINE}' , '', d)} \
    "

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'mali', 'airdigital-mali-${HICHIPSET} kernel-module-mali-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'bluetooth', 'kernel-module-btusb bluez5' , '', d)} \
    "

MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-3798mv200', 'ffmpeg gstreamer1.0-libav' , 'gstreamer1.0-libav ', d)} \
    "

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"

UBI_VOLNAME = "rootfs"
CMDLINE ?= "console=ttyAMA0,115200n8"

KERNEL_MODULE_AUTOLOAD += "mali"

PREFERRED_VERSION_linux-airdigital = "4.4.35"

require conf/machine/include/arm/armv7a/tune-cortexa15.inc
require conf/machine/include/airdigital-oem.inc
