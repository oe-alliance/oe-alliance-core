SUMMARY = "mediatek 7601u"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    ${@bb.utils.contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-mt7601u", "mt7601u", d)} \
    firmware-mt7601u \
    "

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
