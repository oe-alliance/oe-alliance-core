SUMMARY = "TBS STV6120 satellite tuner module"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/tuners/stv6120.c \
    drivers/media/tuners/stv6120.h \
"
TBS_KBUILD_MODULES = "stv6120.o"
TBS_INSTALL_MODULES = "stv6120"
TBS_CCFLAGS = "-DCONFIG_MEDIA_TUNER_STV6120=1 -DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
