KV = "4.8.3"
SRCDATE = "20161025"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "00442b8634d62b5805014c6a1dfb6925"
SRC_URI[sha256sum] = "6de8e033e8bac866dd32e4a3da2d5559c1f0d6b871d523af2ad92b1217ad7a30"
