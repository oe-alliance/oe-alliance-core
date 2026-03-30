SUMMARY = "TBS ASI pseudo frontend module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/tbs_priv.c \
    drivers/media/dvb-frontends/tbs_priv.h \
"
TBS_KBUILD_MODULES = "tbs_priv.o"
TBS_INSTALL_MODULES = "tbs_priv"
TBS_CCFLAGS = "-DCONFIG_DVB_TBSPRIV=1 -DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
