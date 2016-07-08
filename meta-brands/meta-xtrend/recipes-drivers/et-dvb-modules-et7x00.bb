KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160705"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "d6f202208794650e31d2191c6e0e7ccc"
SRC_URI[sha256sum] = "25ed69f819b0977879614313a178922208284e9b67150f776213e1c225315bd6"
