KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170208"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "601695806d7c4174520c50c1892b45cc"
SRC_URI[sha256sum] = "fe5a3e7727055e4729c8a1b44779fab034eb13e9d7986bca8c242293db8cea04"
