SUMMARY = "TBS5927 USB DVB-S/S2 bridge driver"

require tbs-module.inc

TBS_MODULE_DEPENDS = "tbs-frontend-stv091x tbs-tuner-stv6120"
TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs5927.c \
    drivers/media/usb/dvb-usb/tbs5927.h \
    drivers/media/dvb-frontends/stv091x.h \
    drivers/media/tuners/stv6120.h \
"
TBS_KBUILD_MODULES = "dvb-usb-tbs5927.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5927"
TBS_COMPOSITE_OBJECTS = "tbs5927.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5927"
TBS_CCFLAGS = "-DCONFIG_DVB_STV091X=1 -DCONFIG_MEDIA_TUNER_STV6120=1"
TBS_KBUILD_EXTRA_SYMBOLS = "${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-stv091x/Module.symvers ${STAGING_DATADIR}/tbs-module-symvers/tbs-tuner-stv6120/Module.symvers"

RDEPENDS:${PN} += "tbs-frontend-stv091x tbs-tuner-stv6120 firmware-dvb-usb-tbs5927"
