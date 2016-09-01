KV = "4.4.8"
GCC = "4.9.1"
SRCDATE = "20160812"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "c0d762aab7c28ad4a3fa16c95b0795b4"
SRC_URI[sha256sum] = "a2fe7b919b27bd79be4064b592a2e9fe8d031fb6970746e9aefd7a298d1795d8"
