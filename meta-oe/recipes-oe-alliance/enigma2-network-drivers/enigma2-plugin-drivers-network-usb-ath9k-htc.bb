SUMMARY = "WiFi devices for for Atheros AR9001 and AR9002 devices."
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    kernel-module-ath9k-htc \
    firmware-htc7010 \
    firmware-htc9271 \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
