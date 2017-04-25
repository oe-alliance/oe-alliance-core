KV = "4.1.21"
SRCDATE = "20170424"
GCC = "6.3.0"

SRC_URI = "http://xpeedlx.info/${MACHINE_DRIVER}-drivers-${KV}-${GCC}-${SRCDATE}.zip"
require ultramini-dvb-modules.inc

SRC_URI[md5sum] = "036d57fc2083cc4c5c0fb4046d45da52"
SRC_URI[sha256sum] = "b54e83363ee5c03b18cc7c4a13bbe1d9b49c07835f1866c91a2ff31b5416c2e6"
