SUMMARY = "TBS5930 Lite USB DVB-S/S2 bridge driver"
DESCRIPTION = "Driver for the TBS5930 Lite hardware exposed as USB product 5931."

require tbs-module.inc

TBS_MODULE_DEPENDS = "tbs-frontend-gx1133 tbs-frontend-tas2101 tbs-tuner-av201x"
TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs5931.c \
    drivers/media/usb/dvb-usb/tbs5931.h \
    drivers/media/dvb-frontends/gx1133.h \
    drivers/media/dvb-frontends/tas2101.h \
    drivers/media/tuners/av201x.h \
"
TBS_KBUILD_MODULES = "dvb-usb-tbs5931.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5931"
TBS_COMPOSITE_OBJECTS = "tbs5931.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5931"
TBS_CCFLAGS = "-DCONFIG_DVB_GX1133=1 -DCONFIG_DVB_TAS2101=1 -DCONFIG_MEDIA_TUNER_AV201X=1"
TBS_KBUILD_EXTRA_SYMBOLS = "${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-gx1133/Module.symvers ${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-tas2101/Module.symvers ${STAGING_DATADIR}/tbs-module-symvers/tbs-tuner-av201x/Module.symvers"

RDEPENDS:${PN} += "tbs-frontend-gx1133 tbs-frontend-tas2101 tbs-tuner-av201x firmware-dvb-usb-tbs5931"
