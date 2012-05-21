# dreambox-mips32el.inc

DREAMBOX_KERNEL_VERSION = "3.2"
LINUXLIBCVERSION = "3.2"
OLDEST_KERNEL = "3.2"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

require conf/machine/include/dreambox.inc
require conf/machine/include/tune-mips32.inc
