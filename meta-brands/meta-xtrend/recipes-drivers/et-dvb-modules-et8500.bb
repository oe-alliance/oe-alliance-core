KV = "4.4.8"
GCC = "4.9.1"
SRCDATE = "20160504"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc


SRC_URI[md5sum] = "2acaa4dfd1f6bbcc4f36030e4f6e5fbe"
SRC_URI[sha256sum] = "d8034b14df993488f63f832670dc3dfe0f5e6cfc921005d29202e4c00b67c0cc"


