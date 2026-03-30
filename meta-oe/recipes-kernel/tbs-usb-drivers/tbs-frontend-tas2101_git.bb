SUMMARY = "TBS TAS2101 DVB-S/S2 frontend module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/tas2101.c \
    drivers/media/dvb-frontends/tas2101.h \
    drivers/media/dvb-frontends/tas2101_priv.h \
"
TBS_KBUILD_MODULES = "tas2101.o"
TBS_INSTALL_MODULES = "tas2101"
TBS_CCFLAGS = "-DCONFIG_DVB_TAS2101=1 -DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
