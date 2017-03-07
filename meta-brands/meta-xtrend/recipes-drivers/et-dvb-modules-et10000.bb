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
   "e49b6f0255ca77cf4f0e3bcd371de75f", \
   "14dded29b92c317790732e0ae0694633", d)}"
SRC_URI[sha256sum] = "${@bb.utils.contains("DISTRO_NAME", "openvix", \
   "2ffe54bec68ce58edf8fc71611ee8ecb1f9370284a20e877fd7ce87f928c5446", \
   "ebb8baedecb7ea97bbc67e6373934f5082e49df52bb5ae19576e20d7e5cdbc8e", d)}"
