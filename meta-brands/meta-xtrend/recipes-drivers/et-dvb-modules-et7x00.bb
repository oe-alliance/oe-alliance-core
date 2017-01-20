KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170116"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "b756ed4dfb8e2d033413a258c7fa3e00"
SRC_URI[sha256sum] = "36ba5f15559992bce9388dd97a90159b113627d15191af6f63a60208b8117ac3"
