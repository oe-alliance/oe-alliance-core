TARGET_ARCH = "arm"
DEFAULTTUNE = "cortexa15hf-neon-vfpv4"
require conf/machine/include/tune-cortexa15.inc

KERNEL_IMAGETYPE = "zImage"
KERNEL_CONSOLE = "ttyS0,115200n8"

PREFERRED_VERSION_directfb = "1.7.7"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"

require conf/machine/include/xcore-oem.inc