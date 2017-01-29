SRCDATE = "20170118"

KV = "4.8.3"

SRC_URI[md5sum] = "e7c4d1a4d55839f824651bed8c75dc1b"
SRC_URI[sha256sum] = "8c63d7f6758ed5176a1292f3f4951a23fb851e3db3f8026d5cbf47e626d7fe37"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
