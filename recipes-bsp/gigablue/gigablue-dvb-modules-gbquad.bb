SRCDATE = "20131103"

KV = "3.3.8-2.0"

SRC_URI = "http://archiv.openmips.com/gigablue-quad-drivers-${KV}-${SRCDATE}.zip"
SRC_URI[md5sum] = "41bd012f0c792320b27bc13e43c1bb24"
SRC_URI[sha256sum] = "7ced9d7569c4a95e1e01af500a9ffd66b6aa26f7c64a0dc07314dc2f5989590e"

require gigablue-dvb-modules.inc

