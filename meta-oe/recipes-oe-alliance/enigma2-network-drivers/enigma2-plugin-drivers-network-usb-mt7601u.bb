SUMMARY = "mediatek 7601u"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
    mt7601u \
    firmware-mt7601u \
    "

PV = "1.0"
PR = "r1"

ALLOW_EMPTY_${PN} = "1"
