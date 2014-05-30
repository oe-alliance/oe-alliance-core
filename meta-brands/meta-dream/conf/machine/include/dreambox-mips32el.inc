# dreambox-mips32el.inc

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el"

require conf/machine/include/dreambox.inc
require conf/machine/include/tune-mips32.inc

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    dreambox-dvb-modules-${MACHINE} \
    kernel-module-stv0299 \
    "

MACHINE_KERNEL_PR = "r15"
