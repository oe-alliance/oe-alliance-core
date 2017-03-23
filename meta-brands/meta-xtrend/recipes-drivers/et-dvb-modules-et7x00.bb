KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170318"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "be788bbffa66fe37a56c0291207193b9"
SRC_URI[sha256sum] = "974766f1b4fee3db763dccff42366346fc445389f163be3d3bdcb681fa7899d1"
