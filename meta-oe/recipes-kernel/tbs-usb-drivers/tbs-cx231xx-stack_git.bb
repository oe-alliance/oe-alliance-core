SUMMARY = "TBS-modified CX231xx stack for the TBS5990 dual DVB-S/S2 CI tuner"

require tbs-module.inc

TBS_MODULE_DEPENDS = "tbs-frontend-tas2101 tbs-tuner-av201x"
TBS_SOURCE_FILES = " \
    drivers/media/usb/cx231xx/cx231xx-i2c.c \
    drivers/media/usb/cx231xx/cx231xx-cards.c \
    drivers/media/usb/cx231xx/cx231xx-core.c \
    drivers/media/usb/cx231xx/cx231xx-avcore.c \
    drivers/media/usb/cx231xx/cx231xx-pcb-cfg.c \
    drivers/media/usb/cx231xx/cx231xx-dvb.c \
    drivers/media/usb/cx231xx/tbscxci.c \
    drivers/media/usb/cx231xx/cx231xx.h \
    drivers/media/usb/cx231xx/cx231xx-conf-reg.h \
    drivers/media/usb/cx231xx/cx231xx-dif.h \
    drivers/media/usb/cx231xx/cx231xx-pcb-cfg.h \
    drivers/media/usb/cx231xx/cx231xx-reg.h \
    drivers/media/usb/cx231xx/cx231xx-vbi.h \
    drivers/media/usb/cx231xx/tbscxci.h \
    drivers/media/dvb-frontends/tas2101.h \
    drivers/media/tuners/av201x.h \
"

TBS_KBUILD_MODULES = "cx231xx.o cx231xx-dvb-ci.o"
TBS_COMPOSITE_MODULE = "cx231xx"
TBS_COMPOSITE_OBJECTS = "cx231xx-i2c.o cx231xx-cards.o cx231xx-core.o cx231xx-avcore.o cx231xx-pcb-cfg.o"
TBS_COMPOSITE_MODULE_2 = "cx231xx-dvb-ci"
TBS_COMPOSITE_OBJECTS_2 = "cx231xx-dvb.o tbscxci.o"
TBS_INSTALL_MODULES = "cx231xx cx231xx-dvb-ci"
TBS_CCFLAGS = "-DCONFIG_DVB_TAS2101=1 -DCONFIG_MEDIA_TUNER_AV201X=1 -DTBS_CX231XX_5990_ONLY=1"
TBS_KBUILD_EXTRA_SYMBOLS = "${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-tas2101/Module.symvers ${STAGING_DATADIR}/tbs-module-symvers/tbs-tuner-av201x/Module.symvers"

RDEPENDS:${PN} += "tbs-frontend-tas2101 tbs-tuner-av201x firmware-tbs-cx231xx"
