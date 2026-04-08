PACKAGECONFIG_GL:pn-qtbase = "no-opengl linuxfb"
PACKAGECONFIG_FB:pn-qtbase = " "

MACHINE_FEATURES += "qthbbtv qtstalker"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisil-${HICHIPSET}', 'airdigital-bootlogo-${HICHIPSET} airdigital-libs-${HICHIPSET} airdigital-param-${HICHIPSET} airdigital-bootargs-${HICHIPSET} airdigital-fastboot-${HICHIPSET} airdigital-tntfs-${HICHIPSET}' , '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'hisi-recovery', 'airdigital-recovery-${HICHIPSET}' , '', d)} \
    "

TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa7hf"

MKUBIFS_ARGS = "-m 4096 -e 253952 -c 2048"
UBINIZE_ARGS = "-m 4096 -p 256KiB -s 4096"
UBI_VOLNAME = "rootfs"
CMDLINE ?= "console=ttyAMA0,115200n8"

PREFERRED_VERSION_linux-airdigital = "4.4.176"

require conf/machine/include/arm/armv7a/tune-cortexa7.inc
require conf/machine/include/airdigial-ubi-yaffs.inc
require conf/machine/include/airdigital-oem.inc