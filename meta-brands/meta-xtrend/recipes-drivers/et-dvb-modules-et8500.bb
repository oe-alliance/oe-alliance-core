KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20161011"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc


SRC_URI[md5sum] = "f7dd82acda566ab7c8c6a361c32d0f77"
SRC_URI[sha256sum] = "6ce2857499665ce7088fc4dfcd9c937b844beffc9d66b1226ee277a7d96d0d35"


