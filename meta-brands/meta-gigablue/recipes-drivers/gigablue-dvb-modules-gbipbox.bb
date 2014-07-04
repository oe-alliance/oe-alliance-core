SRCDATE = "20140704"

KV = "3.12.1"

SRC_URI[md5sum] = "6cc553053df8c46e8620f52c9667e8ea"
SRC_URI[sha256sum] = "ede8c7d54ffd940f18cfed3145e92e98a111da81d0e3b6639dc3297f8415fb5b"

SRC_URI = "http://archiv.openmips.com/gigablue-drivers-${KV}-gb800xxplus-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

