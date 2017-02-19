TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"
require conf/machine/include/tune-mips32.inc

KERNEL_IMAGETYPE = "vmlinux.gz"
KERNEL_CONSOLE = "ttyS0,115200n8"

require conf/machine/include/xcore-oem.inc