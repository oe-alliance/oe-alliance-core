SRCDATE = "20150608"

KV = "3.14.2"

SRC_URI[md5sum] = "9d28aa5c701d24efafef0481450414fd"
SRC_URI[sha256sum] = "ea8943a11047792bbba259bc446ccc50c3380ca69a38bab2a56350f44c7f1a29"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
