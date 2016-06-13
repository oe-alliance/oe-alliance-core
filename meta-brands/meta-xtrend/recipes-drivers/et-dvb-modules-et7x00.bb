KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160610"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "490e16e334b61cf51d605a1dc510d004"
SRC_URI[sha256sum] = "d43f7e6ac02bdbf440de7eac9f70ffe84e1c455fb4d96a7f71f510ae293bfc18"
