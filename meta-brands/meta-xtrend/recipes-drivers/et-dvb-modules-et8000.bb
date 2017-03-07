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
   "399fda329b4c62d6b79f981644073e30", \
   "00442b8634d62b5805014c6a1dfb6925", d)}"
SRC_URI[sha256sum] = "${@bb.utils.contains("DISTRO_NAME", "openvix", \
   "a550e02f260745196a898a88a1ab0f30aceb8455dd1e8543633e1386de8ee0f8", \
   "6de8e033e8bac866dd32e4a3da2d5559c1f0d6b871d523af2ad92b1217ad7a30", d)}"
