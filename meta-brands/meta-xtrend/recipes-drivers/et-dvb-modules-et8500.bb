KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170318"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "d5f3d995129cdb63ac733aa46c2c23ba"
SRC_URI[sha256sum] = "d5933be0da1883c0033eac91d0575541d66e7bd47f37b1ff0002bebe6affcdd8"
