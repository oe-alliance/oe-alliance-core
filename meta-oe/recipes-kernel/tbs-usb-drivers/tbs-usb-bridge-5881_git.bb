SUMMARY = "TBS5881 USB DVB-T/T2/C CI bridge driver"

require tbs-module.inc

TBS_SOURCE_FILES = " \
    drivers/media/usb/dvb-usb/tbs5881.c \
    drivers/media/usb/dvb-usb/tbs5881.h \
"
TBS_FALLBACK_HEADERS = " \
    drivers/media/dvb-frontends/si2168.h \
    drivers/media/tuners/si2157.h \
"
TBS_KBUILD_MODULES = "dvb-usb-tbs5881.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5881"
TBS_COMPOSITE_OBJECTS = "tbs5881.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5881"

RDEPENDS:${PN} += "kernel-module-si2168 kernel-module-si2157 firmware-dvb-usb-tbs5881 firmware-dvb-fe-si2168"
