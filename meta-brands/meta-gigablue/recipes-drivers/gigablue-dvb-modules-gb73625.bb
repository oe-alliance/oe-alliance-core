SRCDATE = "20160906"

KV = "4.0.1"

SRC_URI[md5sum] = "557965ddeff3e07e8c961cc7cd986cac"
SRC_URI[sha256sum] = "dbfe4708bd36581a1dfe876e12e01e7f58f3dd3b3c63a548258c5a3af51d0355"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
