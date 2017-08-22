SRCDATE = "20170726"

KV = "4.8.3"

SRC_URI[md5sum] = "5dc5b0a178813775e62ecd000f18e543"
SRC_URI[sha256sum] = "74db43b8dfd51a4dcd2b6fef81a623e3cac091d3b2502a8ad6dc6e9e662a90d0"

SRC_URI = "http://impex-sat.de/gigablue/drivers/gigablue-drivers-${KV}-BCM7362-${SRCDATE}.zip"

require gigablue-dvb-modules.inc
