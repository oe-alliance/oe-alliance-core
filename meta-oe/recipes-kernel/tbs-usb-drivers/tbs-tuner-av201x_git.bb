SUMMARY = "TBS AV201x satellite tuner module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/tuners/av201x.c \
    drivers/media/tuners/av201x.h \
    drivers/media/tuners/av201x_priv.h \
"
TBS_KBUILD_MODULES = "av201x.o"
TBS_INSTALL_MODULES = "av201x"
TBS_CCFLAGS = "-DCONFIG_MEDIA_TUNER_AV201X=1 -DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
