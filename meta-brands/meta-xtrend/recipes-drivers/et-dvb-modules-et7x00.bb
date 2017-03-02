KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20170227"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "4dfa80a5eca6755d95afb57a7ef6ed49"
SRC_URI[sha256sum] = "df04cdb8d799494685e39786c60efb58f2d06009743551d6de7d171a9c2336fb"
