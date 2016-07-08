KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160705"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "7069f1940ecb2526fac638cc1c9719e6"
SRC_URI[sha256sum] = "d9b4db0e9ac5c8bb9ba384d89ae9c87a4b1bce42a81725a38016439c01e39b73"
