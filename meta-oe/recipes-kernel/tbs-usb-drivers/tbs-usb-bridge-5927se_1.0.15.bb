SUMMARY = "TBS5927SE USB DVB-S/S2/S2X bridge driver"
DESCRIPTION = "Driver for the TBS5927SE (USB 734c:5929), with AVL62x1 demodulator and RDA5815 tuner."

require tbs5927se-module.inc

TBS_MODULE_DEPENDS = "tbs-frontend-avl62x1 tbs-tuner-rda5815"
TBS_SOURCE_FILES = " \
    dvb-usb/tbs5927se.c \
    dvb-usb/tbs5927se.h \
    frontends/avl62x1.h \
    tuners/rda5815.h \
"
TBS_KBUILD_MODULES = "dvb-usb-tbs5927se.o"
TBS_COMPOSITE_MODULE = "dvb-usb-tbs5927se"
TBS_COMPOSITE_OBJECTS = "tbs5927se.o"
TBS_INSTALL_MODULES = "dvb-usb-tbs5927se"
TBS_KBUILD_EXTRA_SYMBOLS = "${STAGING_DATADIR}/tbs-module-symvers/tbs-frontend-avl62x1/Module.symvers ${STAGING_DATADIR}/tbs-module-symvers/tbs-tuner-rda5815/Module.symvers"

RDEPENDS:${PN} += "tbs-frontend-avl62x1 tbs-tuner-rda5815 firmware-dvb-usb-tbs5927se"
