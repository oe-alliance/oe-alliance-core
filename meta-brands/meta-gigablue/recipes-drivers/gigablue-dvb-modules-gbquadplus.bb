SRCDATE = "20150826"

KV = "3.14.2"

SRC_URI[md5sum] = "c34e7a6e100d868c4a459f85e152ae50"
SRC_URI[sha256sum] = "52893b38b5f731ba2f37e309bbf23d95ba82aed27b54b22d5180db55feca2cd2"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM7356-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
