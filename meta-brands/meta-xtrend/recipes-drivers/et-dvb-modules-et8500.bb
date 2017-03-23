KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170318"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "f5132bb0ae5c32b28bec8163c3651877"
SRC_URI[sha256sum] = "4e461df99b05eeb078a07a36abdc7d143c573e8dbcca15d70a1c124bdc22e862"
