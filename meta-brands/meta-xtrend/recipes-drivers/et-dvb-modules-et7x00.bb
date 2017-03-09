KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170227"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "b606fed0f4db8bcc84a5e478c2f3181e"
SRC_URI[sha256sum] = "aec398673a7f98d935c3d9813c02d2772a7d6ce75206112ce16f476a409ff02c"
