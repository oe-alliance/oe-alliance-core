KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160624"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "2970505d1a9ddbff38ee8f7d061f7ecb"
SRC_URI[sha256sum] = "1d0946782992778915ab2ed29f32aea9f80a68944c08e55263d33c5e4d6d9222"
