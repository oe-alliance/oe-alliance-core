KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20161004"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "21103387c94de03debbfba7bb7693310"
SRC_URI[sha256sum] = "42478d6380c754dd6d22a0ed900c6470c8995fe0fa107e8c2bb1ff9ba28e7dda"
