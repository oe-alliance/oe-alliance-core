SUMMARY = "TBS STV091x DVB-S/S2 frontend module"
DESCRIPTION = "TBS STV091x frontend used by the TBS5927 USB bridge."

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/dvb-frontends/stv091x.c \
    drivers/media/dvb-frontends/stv091x.h \
    drivers/media/dvb-frontends/stv091x_regs.h \
"
TBS_KBUILD_MODULES = "stv091x.o"
TBS_INSTALL_MODULES = "stv091x"
TBS_CCFLAGS = "-DCONFIG_DVB_STV091X=1 -DTBS_LEGACY_DVB_FREQUENCY_KHZ=1"
