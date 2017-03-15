SUMMARY = "new universal rt18xxx kernel driver"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

# kernel-module-rtl8xxxu doesn't seem to work well for rtl8192cu chips.
#

RRECOMMENDS_${PN} = " \
    ${@base_contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu kernel-module-rtl8192cu", "rtl8192cu", d)} \
    firmware-rtl8192cu \
    firmware-rtl8192cufw \
    "

PV = "1.0"
PR = "r5"

ALLOW_EMPTY_${PN} = "1"
