KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170208"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "3e0a549fc31f96c599afe8418aa51a20"
SRC_URI[sha256sum] = "78b21d64597978d8c7220b3da96206f5e786566a55a6811bb8fc7698edc40200"
