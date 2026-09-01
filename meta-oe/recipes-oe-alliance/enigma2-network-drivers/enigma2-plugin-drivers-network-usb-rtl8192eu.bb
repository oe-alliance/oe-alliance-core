SUMMARY = "WiFi devices for Realtek 8192EU chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    rtl8192eu \
    firmware-rtl8192eu \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
