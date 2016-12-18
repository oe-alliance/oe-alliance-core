SRCDATE = "20161215"

KV = "4.0.1"

SRC_URI[md5sum] = "4dd68249c81c462517b4549c095d6eb0"
SRC_URI[sha256sum] = "eb3a07c1446cc87f8fe62efcf5b1e7524a45977299d8b2d9bfe6c518d9898ac2"

SRC_URI = "http://archiv.openmips.com/beta/gigablue-drivers-${KV}-BCM73625-${SRCDATE}a.zip"

require gigablue-dvb-modules.inc
