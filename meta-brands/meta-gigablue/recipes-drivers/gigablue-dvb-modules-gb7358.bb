SRCDATE = "20160301"

KV = "4.0.1"

SRC_URI[md5sum] = "9742961c4fba8458852e14f5f44b2a9a"
SRC_URI[sha256sum] = "face3f5ce5ba91f56b9f01af01e61d1e51b1a47f73529fec9ac167f28251ffd2"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7358-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
