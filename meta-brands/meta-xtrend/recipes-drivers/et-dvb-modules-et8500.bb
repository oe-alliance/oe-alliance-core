KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20170208"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "289c5f52f3b290d179fc807edc788069"
SRC_URI[sha256sum] = "228b3d8c41118d74fbc99744fa386766631b352560b41ae0df7edacad360a2bc"
