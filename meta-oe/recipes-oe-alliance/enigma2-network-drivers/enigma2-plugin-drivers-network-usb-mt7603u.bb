SUMMARY = "mediatek 7603u"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    mt7603u \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
