# dreambox-mips32el-nf.inc

DREAMBOX_KERNEL_VERSION = "2.6.18"
LINUXLIBCVERSION = "2.6.18"
OLDEST_KERNEL = "2.6.18"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el-nf"

require conf/machine/include/dreambox.inc
require conf/machine/include/tune-mips32.inc
