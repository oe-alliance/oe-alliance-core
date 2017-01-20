KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20170111"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "e36cb47ed049885612e204665dfdb845"
SRC_URI[sha256sum] = "11d47db4d39a16f95c81ecc0f0cb154d9fac8d23dbef4c3f6e43c62cbada512f"
