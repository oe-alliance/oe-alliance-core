SUMMARY = "mediatek 7610u"
inherit allarch oea-wifi-driver

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
    ${@wifi_driver(d, 'mt76x0u', 'kernel-module-mt76x0u', 'mt7610u')} \
    "

PV = "1.0"
PR = "r2"

ALLOW_EMPTY:${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
