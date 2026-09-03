SUMMARY = "TBS5580 USB multi-standard DVB-CI bridge driver"

require tbs-module.inc

TBS_MODULE_DEPENDS = "tbs-frontend-si2183 tbs-tuner-av201x"
TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs5580.c \
    drivers/media/usb/dvb-usb/tbs5580.h \
    drivers/media/dvb-frontends/si2183.h \
    drivers/media/tuners/av201x.h \
"
TBS_FALLBACK_HEADERS = "drivers/media/tuners/si2157.h"
TBS_KBUILD_MODULES = "dvb-usb-tbs5580.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5580"
TBS_COMPOSITE_OBJECTS = "tbs5580.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5580"
TBS_CCFLAGS = "-DCONFIG_MEDIA_TUNER_AV201X=1"
TBS_KBUILD_EXTRA_SYMBOLS = "${STAGING_DATADIR}/tbs-module-symvers/tbs-tuner-av201x/Module.symvers"

RDEPENDS:${PN} += "tbs-frontend-si2183 tbs-tuner-av201x kernel-module-si2157 firmware-dvb-usb-tbs5580 firmware-dvb-fe-si2168"
