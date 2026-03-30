SUMMARY = "TBS5925 USB DVB-S/S2 bridge driver"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs5925.c \
    drivers/media/usb/dvb-usb/tbs5925.h \
"
TBS_KBUILD_MODULES = "dvb-usb-tbs5925.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5925"
TBS_COMPOSITE_OBJECTS = "tbs5925.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5925"
TBS_CCFLAGS = "-DCONFIG_DVB_STV090x=1 -DCONFIG_DVB_STB6100=1"

RCONFLICTS:${PN} += "kernel-module-dvb-usb-tbsusb"
RDEPENDS:${PN} += "kernel-module-stv090x kernel-module-stb6100 firmware-dvb-usb-tbs5925"
