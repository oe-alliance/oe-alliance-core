SRCDATE = "20150703"

KV = "3.14.2"

SRC_URI[md5sum] = "babf8ca537fb7a9593a7b3d7cec90274"
SRC_URI[sha256sum] = "42472f09f3085b16a66dbde5848b759e449060ec0ad21f65241cc5038695b395"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
