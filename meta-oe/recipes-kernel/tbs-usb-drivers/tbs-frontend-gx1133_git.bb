SUMMARY = "TBS GX1133 DVB-S/S2 frontend module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/gx1133.c \
    drivers/media/dvb-frontends/gx1133.h \
    drivers/media/dvb-frontends/gx1133_priv.h \
"
TBS_KBUILD_MODULES = "gx1133.o"
TBS_INSTALL_MODULES = "gx1133"
TBS_CCFLAGS = "-DCONFIG_DVB_GX1133=1 -DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
