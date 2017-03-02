KV = "4.4.8"
GCC = "6.3.0"
SRCDATE = "20170224"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "676b11c174dd0e2d4e2cd902f78cf831"
SRC_URI[sha256sum] = "9af0acbd9934a176d51a131eb69e5bc10ecf8409ef8e0c7dc0a108d8249c9aa0"
