SUMMARY = "TBS AVL62x1 satellite demodulator module"

require tbs5927se-module.inc

TBS_SOURCE_FILES = " \
    frontends/avl62x1.c \
    frontends/avl62x1.h \
    frontends/avl62x1_priv.h \
    frontends/avl62x1_regs.h \
"
TBS_KBUILD_MODULES = "avl62x1.o"
TBS_INSTALL_MODULES = "avl62x1"
TBS_CCFLAGS = "-DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"

RDEPENDS:${PN} += "firmware-dvb-demod-avl62x1"
