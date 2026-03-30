SUMMARY = "TBS CXD2878 multi-standard frontend module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/cxd2878.c \
    drivers/media/dvb-frontends/cxd2878.h \
    drivers/media/dvb-frontends/cxd2878_priv.h \
"
TBS_KBUILD_MODULES = "cxd2878.o"
TBS_INSTALL_MODULES = "cxd2878"
TBS_CCFLAGS = " \
    -DCONFIG_DVB_CXD2878=1 \
    -DFEC_1_3=FEC_AUTO \
"
