SUMMARY = "TBS RDA5815 satellite tuner module"

require tbs5927se-module.inc

TBS_SOURCE_FILES = "tuners/rda5815.c tuners/rda5815.h"
TBS_KBUILD_MODULES = "rda5815.o"
TBS_INSTALL_MODULES = "rda5815"
TBS_CCFLAGS = "-DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
