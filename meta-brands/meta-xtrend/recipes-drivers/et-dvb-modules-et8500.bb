KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160701"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "fcb7eb098f4ee45d8be12f4c8a0d88ae"
SRC_URI[sha256sum] = "cadaec94dc0d7d0002b0d1a92bc8313a87d3d00dc832c7544dc5ef7f56c9a7ec"
