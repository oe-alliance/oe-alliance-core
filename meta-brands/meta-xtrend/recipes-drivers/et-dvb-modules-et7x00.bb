KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160705"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "a1391c1374f84e7948bfea05872894c1"
SRC_URI[sha256sum] = "644cfecf19a1e10bb1a1a3d9cba7256b42e1623a77443342260dea88c8acb89c"
