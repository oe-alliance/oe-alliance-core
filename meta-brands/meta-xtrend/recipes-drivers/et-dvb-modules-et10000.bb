KV = "4.8.3"
SRCDATE = "20161025"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "14dded29b92c317790732e0ae0694633"
SRC_URI[sha256sum] = "ebb8baedecb7ea97bbc67e6373934f5082e49df52bb5ae19576e20d7e5cdbc8e"
