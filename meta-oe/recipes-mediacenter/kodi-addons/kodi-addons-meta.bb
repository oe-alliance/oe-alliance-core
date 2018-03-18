SUMMARY = "KODI-Addons-meta"
inherit packagegroup

require conf/license/license-gplv2.inc

PR = "r0"

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} = " \
    kodi-addon-pvr-hts \
    kodi-addon-pvr-iptvsimple \
    kodi-addon-pvr-nextpvr \
    kodi-addon-pvr-mythtv \
    kodi-addon-pvr-dvblink \
    kodi-addon-pvr-vbox \
    kodi-addon-pvr-demo \
    kodi-addon-pvr-njoy \
    kodi-addon-pvr-dvbviewer \
    kodi-addon-pvr-vuplus \
    kodi-addon-pvr-wmc \
    kodi-addon-pvr-filmon \
    kodi-addon-pvr-pctv \
    kodi-addon-pvr-stalker \
    kodi-addon-pvr-vdr.vnsi \
    "

