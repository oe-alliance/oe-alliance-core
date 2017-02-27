SUMMARY = "rt8192cu Proprietary alternative kernel driver"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8192cu", "rtl8192cu", d)} \
    firmware-rtl8192cu \
    firmware-rtl8192cufw \
    "

PV = "1.0"
PR = "r5"

ALLOW_EMPTY_${PN} = "1"
