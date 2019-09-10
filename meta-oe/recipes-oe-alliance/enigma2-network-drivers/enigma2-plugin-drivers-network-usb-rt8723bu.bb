SUMMARY = "WiFi devices for Realtek 8723bu chipsets."
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = "\
    rt8723bu \
    firmware-rtl8723bu \
"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
