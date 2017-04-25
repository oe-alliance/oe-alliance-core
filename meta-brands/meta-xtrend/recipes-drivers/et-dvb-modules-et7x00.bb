KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170424"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "ffb420f7fe0c00603cf3f4d063828cf6"
SRC_URI[sha256sum] = "48f9e09b23081eeab860c41aed08b0d9fbb1a60ab47b85d80322c2897df55e51"
