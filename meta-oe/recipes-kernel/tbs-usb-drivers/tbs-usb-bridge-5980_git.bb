SUMMARY = "TBS5980 USB DVB-S/S2 CI bridge driver"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs-qbox2ci.c \
    drivers/media/usb/dvb-usb/tbs-qbox2ci.h \
"
TBS_KBUILD_MODULES = "dvb-usb-tbsqbox2ci.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbsqbox2ci"
TBS_COMPOSITE_OBJECTS = "tbs-qbox2ci.o"
TBS_INSTALL_MODULES = "dvb-usb-tbsqbox2ci"
TBS_CCFLAGS = "-DCONFIG_DVB_STV090x=1 -DCONFIG_DVB_STB6100=1"

RCONFLICTS:${PN} += "kernel-module-dvb-usb-tbsusb"
RDEPENDS:${PN} += "kernel-module-stv090x kernel-module-stb6100 firmware-dvb-usb-tbs5980"
