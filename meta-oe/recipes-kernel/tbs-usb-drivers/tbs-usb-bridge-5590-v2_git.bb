SUMMARY = "TBS5590 V2 USB multi-standard DVB/ASI bridge driver"

require tbs-module.inc

TBS_MODULE_DEPENDS = "tbs-frontend-si2183 tbs-frontend-cxd2878 tbs-frontend-m88rs6060 tbs-frontend-tbs-priv tbs-tuner-av201x"
TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs5590.c \
    drivers/media/usb/dvb-usb/tbs5590.h \
    drivers/media/dvb-frontends/si2183.h \
    drivers/media/dvb-frontends/cxd2878.h \
    drivers/media/dvb-frontends/m88rs6060.h \
    drivers/media/dvb-frontends/tbs_priv.h \
    drivers/media/tuners/av201x.h \
"
TBS_FALLBACK_HEADERS = "drivers/media/tuners/si2157.h"
TBS_KBUILD_MODULES = "dvb-usb-tbs5590.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5590"
TBS_COMPOSITE_OBJECTS = "tbs5590.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5590"
TBS_CCFLAGS = "-DCONFIG_DVB_CXD2878=1 -DCONFIG_DVB_TBSPRIV=1 -DCONFIG_MEDIA_TUNER_AV201X=1"
TBS_KBUILD_EXTRA_SYMBOLS = "${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-cxd2878/Module.symvers ${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-tbs-priv/Module.symvers ${STAGING_DATADIR}/tbs-module-symvers/tbs-tuner-av201x/Module.symvers"

RDEPENDS:${PN} += "tbs-frontend-si2183 tbs-frontend-cxd2878 tbs-frontend-m88rs6060 tbs-frontend-tbs-priv tbs-tuner-av201x kernel-module-si2157 firmware-dvb-usb-tbs5590 firmware-dvb-fe-si2168"
