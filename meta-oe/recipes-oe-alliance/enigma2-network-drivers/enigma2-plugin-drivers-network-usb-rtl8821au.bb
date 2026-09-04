SUMMARY = "WiFi devices for Realtek 8811AU and 8821AU chipsets."
inherit allarch nospdx

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
        rtl8821au \
"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
