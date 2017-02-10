KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20170208"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "0c055823f2c299581d1d1ea77b930634"
SRC_URI[sha256sum] = "249acb919615ce4cafdb9eb928da29e4495718c6973d8b1b2619a85fd200b6b8"
