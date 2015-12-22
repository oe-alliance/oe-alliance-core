KV = "3.18.24"
SRCDATE = "20151201"

SRC_URI = "http://source.mynonpublic.com/protek/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"
require protek-dvb-modules.inc

SRC_URI[md5sum] = "a044a591f1707664d09981917e8df9da"
SRC_URI[sha256sum] = "8385ada86915cfe2ed3ad00200549c5d32a91bd53b4b57ce6b3cfaf7d9cf9190"
