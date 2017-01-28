SUMMARY = "Driver for ZyDAS ZD1211, ZyDAS ZD1211B, Atheros AR5007UG"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-zd1211rw \
    firmware-zd1211 \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
