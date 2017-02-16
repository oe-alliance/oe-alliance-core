TARGET_ARCH = "arm"
DEFAULTTUNE = "armv7ahf-neon"
require conf/machine/include/arm/arch-armv7a.inc

KERNEL_IMAGETYPE = "zImage"
KERNEL_CONSOLE = "ttyS0,115200n8"

require conf/machine/include/xcore-oem.inc