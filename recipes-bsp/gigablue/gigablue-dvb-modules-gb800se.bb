SRCDATE = "20130216"

KV = "3.1.1"

SRC_URI[md5sum] = "9ee7b0561342e44a56ad94a9c7965fcc"
SRC_URI[sha256sum] = "a9eb9565a019bb85d8b68295e2f167d2615ed3d1c4c1ba4df6bb640ddfe58a14"

SRC_URI = "http://archiv.openmips.com/gigablue-gb800xx-drivers-${KV}-${SRCDATE}.zip"

require gigablue-dvb-modules.inc

