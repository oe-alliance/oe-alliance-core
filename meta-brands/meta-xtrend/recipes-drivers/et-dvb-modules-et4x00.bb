KV = "4.8.3"
SRCDATE = "20161025"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

# Set OpenViX to use 4.0.1
#
KV_openvix = "4.0.1"
SRCDATE_openvix = "20160420"
SRC_URI_openvix = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

# Correct checksums for 4.0.1 usage
#
SRC_URI[md5sum] = "${@bb.utils.contains("DISTRO_NAME", "openvix", \
   "3b7a8d6648f97e9f51a37ade66725492", \
   "854837fa29cff93a82176a17347598e9", d)}"
SRC_URI[sha256sum] = "${@bb.utils.contains("DISTRO_NAME", "openvix", \
   "fde08b649f16b18f68535f68ae961dba56f6c7947c6fab4bc9c21d955154087d", \
   "33dfd2d38572c7cc9f018744630facb59d0b0c12d224312f199e8be8d261569d", d)}"
