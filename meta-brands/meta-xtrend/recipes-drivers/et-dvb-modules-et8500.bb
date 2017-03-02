KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20170224"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "d29576ba1e83c2aa4e7230e2f5333d03"
SRC_URI[sha256sum] = "8dc23f5cf46c7a866b3c630dfb23b38e0dc5473396b844b2e96c754b16a5231e"
