SUMMARY = "mediatek 7610u"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = " \
     mt7610u \
    "

PV = "1.0"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"
