KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170424"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "ef54c0dc19c0b70b4f64e125fefcfa30"
SRC_URI[sha256sum] = "1d90f5523234ba745250e72acd64fe07e16f4fd3e9e1c04a2b0a159523a2d150"
