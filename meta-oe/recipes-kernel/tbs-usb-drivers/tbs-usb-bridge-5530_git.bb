SUMMARY = "TBS5530 USB multi-standard DVB bridge driver"

require tbs-module.inc

TBS_MODULE_DEPENDS = "tbs-frontend-cxd2878 tbs-frontend-m88rs6060"
TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs5530.c \
    drivers/media/usb/dvb-usb/tbs5530.h \
    drivers/media/dvb-frontends/cxd2878.h \
    drivers/media/dvb-frontends/m88rs6060.h \
"
TBS_KBUILD_MODULES = "dvb-usb-tbs5530.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5530"
TBS_COMPOSITE_OBJECTS = "tbs5530.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5530"
TBS_CCFLAGS = "-DCONFIG_DVB_CXD2878=1 -DCONFIG_DVB_M88RS6060=1"
TBS_KBUILD_EXTRA_SYMBOLS = "${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-cxd2878/Module.symvers"

RDEPENDS:${PN} += "tbs-frontend-cxd2878 tbs-frontend-m88rs6060 firmware-dvb-usb-tbs5530"
