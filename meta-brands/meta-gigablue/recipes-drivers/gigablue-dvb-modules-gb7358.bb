SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "35d8cbc3d2b227d17837fbfe67c69549"
SRC_URI[sha256sum] = "984a46183305a7b2b9eb384c48f4d525639558259960e5f5752eab5389fdd7e1"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
