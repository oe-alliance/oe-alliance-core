KV = "4.10.6"
SRCDATE = "20200402"

SRC_URI = "http://source.mynonpublic.com/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "7aa2761146a7c76aacdd41c4001d8ca4"
SRC_URI[sha256sum] = "f44853fcf9108fd17d146e5a9b919c4cf359e5dab8e77c830ece111fbb319957"
