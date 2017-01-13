KV = "4.8.3"
SRCDATE = "20161025"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "854837fa29cff93a82176a17347598e9"
SRC_URI[sha256sum] = "33dfd2d38572c7cc9f018744630facb59d0b0c12d224312f199e8be8d261569d"
