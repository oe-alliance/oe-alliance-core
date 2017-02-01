KV = "4.1.21"
SRCDATE = "20160613"
GCC = "5.3.0"

SRC_URI = "http://xpeedlx.info/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "12efd628393b59c06b6db49cae5ba1ff"
SRC_URI[sha256sum] = "33a2c73ffd83ae5a86eea3cbb450cbb9e8da929148833bf1ddd1f9fe1712cda8"
