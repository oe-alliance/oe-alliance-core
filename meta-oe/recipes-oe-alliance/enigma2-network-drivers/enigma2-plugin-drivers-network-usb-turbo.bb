SUMMARY = "Driver for Vuplus Tuner Turbo"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    vuplus-tuner-turbo \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
